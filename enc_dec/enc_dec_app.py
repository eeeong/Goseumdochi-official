from flask import Flask, request, jsonify
from fastapi import FastAPI
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding
import base64

app = Flask(__name__)

@app.route('/encrypt', methods=['POST'])
def encrypt():
    data = request.get_json()
    plaintext = data.get('plaintext')
    password = data.get('password')

    encrypted_string = encrypt_string_AES(plaintext, password)
    return jsonify({'encrypted_text': encrypted_string})

@app.route('/decrypt', methods=['POST'])
def decrypt():
    data = request.get_json()
    encrypted_base64 = data.get('encrypted_text')
    password = data.get('password')

    decrypted_string = decrypt_string_AES(encrypted_base64, password)
    return jsonify({'decrypted_text': decrypted_string})

def encrypt_string_AES(plaintext, password):
    key = password.encode("utf-8").ljust(16, b"\x00")[:16]
    cipher = Cipher(algorithms.AES(key), modes.ECB(), backend=default_backend())
    encryptor = cipher.encryptor()

    if isinstance(plaintext, str):
        plaintext = plaintext.encode("utf-8")

    padder = padding.PKCS7(algorithms.AES.block_size).padder()
    padded_plaintext = padder.update(plaintext) + padder.finalize()

    encrypted_ciphertext = encryptor.update(padded_plaintext) + encryptor.finalize()

    encrypted_base64 = base64.b64encode(encrypted_ciphertext).decode("utf-8")

    return encrypted_base64

def decrypt_string_AES(encrypted_base64, password):
    key = password.encode("utf-8").ljust(16, b"\x00")[:16]
    cipher = Cipher(algorithms.AES(key), modes.ECB(), backend=default_backend())
    decryptor = cipher.decryptor()

    encrypted_ciphertext = base64.b64decode(encrypted_base64.encode("utf-8"))

    decrypted_padded_plaintext = decryptor.update(encrypted_ciphertext) + decryptor.finalize()

    unpadder = padding.PKCS7(algorithms.AES.block_size).unpadder()
    decrypted_plaintext = unpadder.update(decrypted_padded_plaintext) + unpadder.finalize()

    return decrypted_plaintext.decode("utf-8")

if __name__ == '__main__':
    app.run(debug=True)
