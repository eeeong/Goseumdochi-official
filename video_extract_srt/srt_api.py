import os
import requests
import subprocess
from flask import Flask, request, jsonify
from transformers import pipeline
import schedule
import time
import threading
import uuid

import warnings
warnings.filterwarnings('ignore')

app = Flask(__name__)

# 동영상 및 자막 파일 저장 경로
VIDEO_DIR = './static/videos/'
OUTPUT_DIR = './static/output/'

# 디렉토리가 없으면 생성
os.makedirs(VIDEO_DIR, exist_ok=True)
os.makedirs(OUTPUT_DIR, exist_ok=True)

# 모델 로드 (한 번만 로드하여 성능 최적화)
asr_model = pipeline("automatic-speech-recognition", model="openai/whisper-large")

# 동영상 다운로드 함수
def download_video(video_url, output_path):
    response = requests.get(video_url, stream=True)
    with open(output_path, 'wb') as file:
        for chunk in response.iter_content(chunk_size=1024):
            if chunk:
                file.write(chunk)
    return output_path

# 동영상에서 오디오 추출 함수
def extract_audio_from_video(video_path, audio_output_path):
    command = ['ffmpeg', '-i', video_path, '-q:a', '0', '-map', 'a', audio_output_path]
    
    # ffmpeg 명령어 실행
    process = subprocess.run(command, stderr=subprocess.PIPE, stdout=subprocess.PIPE)
    
    # ffmpeg 실행 결과 출력 및 오류 확인
    if process.returncode != 0:
        print(f"ffmpeg error: {process.stderr.decode('utf-8')}")
        raise Exception("Failed to extract audio from video")
    else:
        print(f"Audio successfully extracted to {audio_output_path}")

# 한국어 자막 생성 함수
def generate_korean_subtitles(audio_path):
    try:
        result = asr_model(audio_path)
        return result.get('text', '')
    except Exception as e:
        raise Exception(f"Subtitle generation failed: {str(e)}")

# 다운로드한 동영상을 처리하는 API 엔드포인트
@app.route('/request_text', methods=['POST'])
def download_and_process_video():
    data = request.get_json()
    video_url = data.get('video_url')

    if not video_url:
        return jsonify({"error": "Video URL is required"}), 400

    # 고유한 파일명 생성
    video_filename = f"{uuid.uuid4()}.mp4"
    audio_filename = f"{uuid.uuid4()}.wav"
    video_path = os.path.join(VIDEO_DIR, video_filename)
    audio_path = os.path.join(VIDEO_DIR, audio_filename)

    # 1. 동영상 다운로드
    try:
        download_video(video_url, video_path)
        print('finish download')
    except Exception as e:
        return jsonify({"error": f"Failed to download video: {str(e)}"}), 500

    # 2. 동영상에서 오디오 추출
    try:
        extract_audio_from_video(video_path, audio_path)
        print('finish extract')
    except Exception as e:
        return jsonify({"error": f"Audio extraction failed: {str(e)}"}), 500

    # 3. 한국어 자막 생성
    try:
        subtitles = generate_korean_subtitles(audio_path)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

    # 처리 완료된 자막 및 동영상 파일 경로 반환
    return jsonify({
        "text": subtitles,
        "video_path": video_path
    }), 200

# 하루마다 동영상 자동 삭제
def delete_old_videos(folder_path):
    for filename in os.listdir(folder_path):
        file_path = os.path.join(folder_path, filename)
        if os.path.isfile(file_path):
            os.remove(file_path)

# 스케줄러 실행
def run_scheduler():
    schedule.every().day.at("00:00").do(delete_old_videos, folder_path=VIDEO_DIR)
    schedule.every().day.at("00:00").do(delete_old_videos, folder_path=OUTPUT_DIR)
    while True:
        schedule.run_pending()
        time.sleep(60)

if __name__ == '__main__':
    # 스케줄러를 별도의 스레드에서 실행
    scheduler_thread = threading.Thread(target=run_scheduler)
    scheduler_thread.daemon = True
    scheduler_thread.start()

    # Flask 애플리케이션 실행
    app.run(debug=True)
