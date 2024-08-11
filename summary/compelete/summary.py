from flask import Flask, request, jsonify
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
import torch

app = Flask(__name__)

tokenizer = AutoTokenizer.from_pretrained("./kobart-summarization-finetuned")
model = AutoModelForSeq2SeqLM.from_pretrained("./kobart-summarization-finetuned")

device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
model.to(device)

@app.route('/summarize', methods=['POST'])
def summarize():
    data = request.json
    text = data.get("text", "")

    if not text:
        return jsonify({"error": "No text provided"}), 400

    inputs = tokenizer(text, return_tensors="pt", max_length=1024, truncation=True, padding="max_length")
    inputs = {key: value.to(device) for key, value in inputs.items()}

    summary_ids = model.generate(inputs["input_ids"], max_length=128, num_beams=4, early_stopping=True)
    summary = tokenizer.decode(summary_ids[0], skip_special_tokens=True)

    return jsonify({"summary": summary})

if __name__ == '__main__':
    app.run(debug=True)
