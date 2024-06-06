from flask import Flask, request, jsonify
import pandas as pd
import joblib
import re

app = Flask(__name__)

tfidf_vectorizer = joblib.load('tfidf_vectorizer.pkl')
knn = joblib.load('knn_model.pkl')

df = pd.read_csv('recommand_data.csv', encoding='CP949')
indices_to_remove = df[(df['학위과정명'] == '박사')|
                        (df['학위과정명'] == '박사+석박사통합')|
                        (df['학위과정명'] == '석박사통합')|
                        (df['학위과정명'] == '석사')|
                        (df['학위과정명'] == '석사+박사')|
                        (df['학위과정명'] == '석사+박사+석박사통합')|
                        (df['학위과정명'] == '석사+석박사통합')|
                        (df['학위과정명'] == '전문기술석사')|
                        (df['학과상태명'] == '폐과')|
                        (df['학교구분명'] == '일반대학원')|
                        (df['학교구분명'] == '전문대학원')|
                        (df['학교구분명'] == '특수대학원')|
                        (df['학교학과특성명'] == '전문기술석사과정')|
                        (df['학교학과특성명'] == '학석사통합과정')
].index
df = df.drop(indices_to_remove)
df = df.drop(['연도', '시도코드', '시군구코드','학과코드명(7대계열)','표준분류계열코드','수정일자','데이터기준일자','학과상태명','시군구명','시도명'], axis=1)
df = df.fillna('')
df['주요교과목명'] = df['주요교과목명'].fillna('')

def replace_text(text):
    pattern = r'\b(?!C\+\+)\w+(\s*\+\s*\w+)*\b'
    
    def replacer(match):
        return match.group(0).replace('+', ', ')

    result = re.sub(pattern, replacer, text)
    return result

def recommend_major(major_subject, n_recommendations=15245):
    input_vector = tfidf_vectorizer.transform([major_subject]).toarray()
    distances, indices = knn.kneighbors(input_vector, n_neighbors=n_recommendations)
    
    recommendations = df.iloc[indices[0]].copy()
    recommendations['distance'] = distances[0]
    recommendations = recommendations.sort_values(by='distance')

    recommendations['주요교과목명'] = recommendations['주요교과목명'].apply(lambda x: replace_text(x))
    recommendations['관련직업명'] = recommendations['관련직업명'].apply(lambda x: replace_text(x))
    
    return recommendations

@app.route('/recommend', methods=['POST'])
def recommend():
    data = request.get_json()
    major_subject = data['major_subject']
    n_recommendations = data.get('n_recommendations', 15245)
    
    recommendations = recommend_major(major_subject, n_recommendations)
    response = recommendations.to_dict(orient='records')
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True)
