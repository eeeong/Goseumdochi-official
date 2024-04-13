from flask import Flask, request, jsonify
from transformers import AutoModelForSequenceClassification, AutoTokenizer
import torch

app = Flask(__name__)

model_path = "./models/bad_words"
MODEL_NAME = "beomi/KcELECTRA-base-v2022"
model = AutoModelForSequenceClassification.from_pretrained(model_path)
tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)

def classify_text(text):
    inputs = tokenizer(text, return_tensors="pt", padding=True, truncation=True)
    outputs = model(**inputs)
    logits = outputs.logits
    probabilities = torch.softmax(logits, dim=1)
    predicted_label = torch.argmax(probabilities, dim=1).item()
    return predicted_label

@app.route('/classify', methods=['POST'])
def classify():
    if request.method == 'POST':
        text = request.json.get('text')
        if text:
            label = classify_text(text)
            if label == 0:
                result = {"label": 1}
            else:
                result = {"label": 0}
            return jsonify(result)
        else:
            return jsonify({"error": "Text field is missing"}), 400

if __name__ == '__main__':
    app.run(debug=True)
