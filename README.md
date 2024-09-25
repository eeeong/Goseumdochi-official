# Goseumdochi-Official API 문서

## MIDAS 2024 팀 프로젝트  
### AI(MSY)

---

## API 사용법

### 1. 암호화 API  
- **엔드포인트:** `http://34.64.237.19:5000/encrypt`  
- **요청 방식:** POST

#### 1.1. 입력 파라미터  
- `text`: 암호화할 문자열  
- `password`: 암호화 시 사용할 비밀번호 (비밀번호를 잊으면 복호화가 불가능합니다.)

#### 1.2. 출력  
- 암호화된 문자열이 반환됩니다. 사용된 암호화 방식은 AES(Advanced Encryption Standard)입니다.

---

### 2. 복호화 API  
- **엔드포인트:** `http://34.64.237.19:5000/decrypt`  
- **요청 방식:** POST

#### 2.1. 입력 파라미터  
- `text`: 복호화할 암호화된 문자열  
- `password`: 암호화 시 사용된 비밀번호 (암호화 시 사용된 비밀번호와 동일해야 합니다.)

#### 2.2. 출력  
- 복호화된 평문이 반환됩니다.

---

### 3. 비방목적 글 분류 AI API  
- **엔드포인트:** `http://34.64.237.19:5001/classify`  
- **요청 방식:** POST

#### 3.1. 입력 파라미터  
- `text`: 분류할 문자열

#### 3.2. 출력  
- 해당 콘텐츠가 비방목적에 해당하는지 여부를 나타내는 라벨값을 반환합니다:
  - `1`: 비방 목적의 콘텐츠  
  - `0`: 비방 목적이 아닌 콘텐츠

---

### 4. 대학-학과 추천 AI API  
- **엔드포인트:** `http://34.64.237.19:5002/recommend`  
- **요청 방식:** POST

#### 4.1. 입력 파라미터  
- `major_subject`: 관심 있는 과목 (문자열)  
- `n_recommendations`: 추천 결과의 개수 (정수)

#### 4.2. 출력  
- 추천된 대학과 관련 학과에 대한 정보를 JSON 형태로 반환합니다.  
- **예시 입력:** `major_subject = "IoT 및 네트워크 구축"`  
- **예시 출력:**

```json
{
    "distance": 0.4881545189134262,
    "관련직업명": "네트워크프로그래머, 스마트폰앱개발자, 시스템소프트웨어개발자, 시스템엔지니어, 컴퓨터공학기술자,  컴퓨터시스템설계분석가, 컴퓨터프로그래머, 통신공학기술자",
    "단과대학명": "SCH미디어랩스",
    "대학자체계열명": "공학",
    "수업연한": "4년",
    "입학정원수": 45,
    "졸업자수": 33,
    "주야과정명": "주간",
    "주요교과목명": "C 프로그래밍, C++ 프로그래밍, IoT 개론, IoT 네트워크 및 응용, IoT 데이터 분석, IoT 센서와 제어, IoT 융합특론, IoT 전문가 특강, IoT 플랫폼, IoT보안, 객체지향프로그래밍, 고급 웹 프로그래밍, 기초수학, 네트워크 프로그래밍, 논리회로, 데이터분석 기초, 리눅스 프로그래밍, 마이크로프로세서, 머신러닝 이해, 모바일 프로그래밍, 산학캡스톤디자인, 소프트웨어 공학, 스마트 IoT 시스템 설계, 알고리즘 활용, 웹 프로그래밍, 윈도우 프로그래밍, 융합머신러닝, 인간과 컴퓨터,  인공지능, 임베디드 SW, 임베디드 시스템 고급, 임베디드 시스템 기초, 자료구조, 전공 영어 1, 전공 영어 2, 전기전자회로, 정보보안, 정보통신개론, 졸업작품개발 (캡스톤디자인)+졸업작품설계 (캡스톤디자인)+차세대 IoT 네트워크, 차세대 통신 네트워크, 창의공학설계, 캡스톤디자인 2, 컴퓨터 네트워크, 클라우드 컴퓨팅 개론, 확률과데이터분석",
    "학과명": "사물인터넷학과",
    "학교구분명": "대학교",
    "학교명": "순천향대학교",
    "학교학과특성명": "일반과정",
    "학위과정명": "학사"
}
```
---
### 5. 텍스트 요약 API  
- **엔드포인트:** `http://34.64.237.19:5003/summarize`  
- **요청 방식:** POST

#### 5.1. 입력 파라미터  
- `text`: 요약할 텍스트 (문자열)

#### 5.2. 출력  
- 요약된 텍스트가 반환됩니다.

---

### 6. 수학 문제 풀이 API  
- **엔드포인트:** `http://34.64.237.19:5004/solve`  
- **요청 방식:** POST

#### 6.1. 입력 파라미터  
- `problem`: 풀고자 하는 수학 문제 (문자열)

#### 6.2. 출력  
- 입력된 수학 문제에 대한 해답을 포함하는 JSON 객체가 반환됩니다.  
- **예시 입력:** `problem = "Find the value of $x$ that satisfies the equation $4x+5 = 6x+7$."`
  
- **예시 출력:**  

```json
{
    "solution": "To solve the equation \\(4x + 5 = 6x + 7\\), we need to isolate the variable \\(x\\). Here are the steps to do that:\n\n1. Start with the given equation:\n   \\[\n   4x + 5 = 6x + 7\n   \\]\n\n2. Subtract \\(4x\\) from both sides of the equation to eliminate \\(x\\) from the left side:\n   \\[\n   4x + 5 - 4x = 6x + 7 - 4x\n   \\]\n   Simplifying both sides, we get:\n   \\[\n   5 = 2x + 7\n   \\]\n\n3. Next, subtract 7 from both sides to isolate the term with \\(x\\) on the right side:\n   \\[\n   5 - 7 = 2x + 7 - 7\n   \\]\n   Simplifying both sides, we get:\n   \\[\n   -2 = 2x\n   \\]\n\n4. Finally, divide both sides by 2 to solve for \\(x\\):\n   \\[\n   \\frac{-2}{2} = \\frac{2x}{2}\n   \\]\n   Simplifying both sides, we get:\n   \\[\n   -1 = x\n   \\]\n   or\n   \\[\n   x = -1\n   \\]\n\nTherefore, the value of \\(x\\) that satisfies the equation is \\(\\boxed{-1}\\)."
}
