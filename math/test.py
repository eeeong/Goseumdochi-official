import torch
from torch.utils.data import DataLoader, Dataset
from transformers import AutoModelForCausalLM, AutoTokenizer, AdamW, get_scheduler
from tqdm import tqdm
from datetime import datetime
import os
import json

class MathDataset(Dataset):
    
    def __init__(self, base_path, tokenizer=None, subset='train', max_length=512):
        self.base_path = base_path
        self.subset = subset
        self.tokenizer = tokenizer
        self.max_length = max_length
        
        self.problems, self.solutions = self.load_data()

    def load_data(self):
        types_dir = os.listdir(os.path.join(self.base_path, self.subset))
        problems = []
        solutions = []

        for type_dir in types_dir:
            type_path = os.path.join(self.base_path, self.subset, type_dir)
            math_files = os.listdir(type_path)[:200]

            for math_file in math_files:
                math_path = os.path.join(type_path, math_file)
                with open(math_path, 'r') as f:
                    sample = json.load(f)
                    problems.append(sample['problem'])
                    solutions.append(sample['solution'])

        return problems, solutions
        
    def __len__(self):
        return len(self.problems)
    
    def __getitem__(self, idx):
        problem = self.problems[idx]
        solution = self.solutions[idx]

        if self.tokenizer:
            problem_encodings = self.tokenizer(problem, truncation=True, padding='max_length', max_length=self.max_length)
            solution_encodings = self.tokenizer(solution, truncation=True, padding='max_length', max_length=self.max_length)

            labels = solution_encodings['input_ids']
            labels = [-100 if token == self.tokenizer.pad_token_id else token for token in labels]

            return {
                'input_ids': torch.tensor(problem_encodings['input_ids']),
                'attention_mask': torch.tensor(problem_encodings['attention_mask']),
                'labels': torch.tensor(labels)
            }
        else:
            return problem, solution
        
# 모델 및 토크나이저 설정
model_name = "Qwen/Qwen2.5-Math-1.5B-Instruct"
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

model = AutoModelForCausalLM.from_pretrained(
    model_name, 
    device_map="auto",
    torch_dtype="auto",
    trust_remote_code=True,
)
tokenizer = AutoTokenizer.from_pretrained(model_name, trust_remote_code=True)

model.to(device)

# Dataset 로딩
train_dataset = MathDataset(base_path="", tokenizer=tokenizer, subset='train')
val_dataset = MathDataset(base_path="", tokenizer=tokenizer, subset='test')

train_loader = DataLoader(train_dataset, batch_size=2, shuffle=True)
val_loader = DataLoader(val_dataset, batch_size=2)

# 옵티마이저 및 스케줄러 설정
optimizer = AdamW(model.parameters(), lr=5e-5)
num_epochs = 10
num_training_steps = num_epochs * len(train_loader)
lr_scheduler = get_scheduler(
    name="linear", optimizer=optimizer, num_warmup_steps=0, num_training_steps=num_training_steps
)

# 학습 진행
progress_bar = tqdm(range(num_training_steps))
model.train()

for epoch in range(num_epochs):
    for batch in train_loader:
        optimizer.zero_grad()
        
        # 배치 데이터 추출
        input_ids = batch['input_ids'].to(device)
        attention_mask = batch['attention_mask'].to(device)
        labels = batch['labels'].to(device)
        
        # 모델에 입력 및 역전파
        outputs = model(input_ids=input_ids, attention_mask=attention_mask, labels=labels)
        loss = outputs.loss
        loss.backward()
        
        optimizer.step()
        lr_scheduler.step()
        progress_bar.update(1)

    # 검증
    model.eval()
    val_loss = 0
    for batch in val_loader:
        with torch.no_grad():
            input_ids = batch['input_ids'].to(device)
            attention_mask = batch['attention_mask'].to(device)
            labels = batch['labels'].to(device)
            outputs = model(input_ids=input_ids, attention_mask=attention_mask, labels=labels)
            val_loss += outputs.loss.item()

    avg_val_loss = val_loss / len(val_loader)
    print(f"Epoch {epoch + 1} / {num_epochs}, Validation Loss: {avg_val_loss}")
    model.train()

# 학습된 모델 저장
current_date = datetime.now().strftime('%Y-%m-%d')
save_path = f"model_{model_name.replace('/', '_')}_{current_date}.bin"
model.save_pretrained(save_path)
tokenizer.save_pretrained(save_path)

print(f"Model saved to {save_path}")
