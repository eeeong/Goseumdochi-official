from flask import Flask, request, jsonify
from transformers import AutoModelForCausalLM, AutoTokenizer
import torch

app = Flask(__name__)

# 저장된 모델 경로
model_path = "./model_0925"
device = "cuda" if torch.cuda.is_available() else "cpu"

# 모델과 토크나이저 로드
model = AutoModelForCausalLM.from_pretrained(
    model_path,
    torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32,
    device_map="auto" if torch.cuda.is_available() else None
)
tokenizer = AutoTokenizer.from_pretrained(model_path)

# API 엔드포인트 정의
@app.route('/solve', methods=['POST'])
def solve_math_problem():
    try:
        data = request.json
        if 'problem' not in data:
            return jsonify({"error": "Please provide a math problem."}), 400

        prompt = data['problem']

        # CoT 메시지 템플릿 생성
        messages = [
            {"role": "system", "content": "Please reason step by step, and put your final answer within \\boxed{}."},
            {"role": "user", "content": prompt}
        ]

        # 입력 텍스트 생성 및 토크나이징
        text = tokenizer.apply_chat_template(messages, tokenize=False, add_generation_prompt=True)
        model_inputs = tokenizer([text], return_tensors="pt").to(device)

        # 모델 예측 생성
        generated_ids = model.generate(
            **model_inputs,
            max_new_tokens=512
        )
        generated_ids = [
            output_ids[len(input_ids):] for input_ids, output_ids in zip(model_inputs.input_ids, generated_ids)
        ]

        # 결과 디코딩
        response = tokenizer.batch_decode(generated_ids, skip_special_tokens=True)[0]

        return jsonify({"solution": response})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
