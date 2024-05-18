# Goseumdochi-official
MIDAS 2024 Team Project  
Ai made by MSY  
# API 사용법
## 암호화 api  
1. enc(암호화)  
1-1. 주소 : http://34.64.149.50:5000/encrypt
1-2. input 매개변수  
        
        1-2-1. plaintext = 암호화 할 문자열
        1-2-2. password = 암호화시 필요 비밀번호( 잊어버릴시 복호화 불가 )
    1-3. output 내용   
    
        암호화된 문자열로 나옴. 방식(AES방식)
2. dec(복호화)  
2-1. 주소 : http://34.64.149.50:5000/decrypt  
2-2. input 매개변수

        2-2-1. encrypted_text = 복호화 할 암호문(문자열)
        2-2-2. password = 복호화시 사용될 비밀번호( 암호화 비밀번호랑 동일해야함. )

    2-3. output 내용  

        복호화된 평문으로 나옴.

## 비방목적 글 분류 ai api
1. 비방목적 분류  
1-1. 주소 : http://34.64.149.50:5001/classify  
1-2. input 매개변수

        1-2-1. text = 분류할 글(문자열)
    1-3.output내용

        label값 반환( 1 이면 비방 0 이면 비방아님 )