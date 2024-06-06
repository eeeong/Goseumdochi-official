import { useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function DirectorNoticeWrite() {
    const location = useLocation();
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');

    const { user } = location.state || {};

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    }

    const handleContentChange = (event) => {
        setContent(event.target.value);
    }

    const handleSubmit = async () => {
        if (!user || !user.id) {
            alert('사용자 정보가 올바르지 않습니다.');
            return;
        }

        const notice = {
            title: title,
            content: content,
            directorId: user.id // 작성자의 ID를 전달
        };

        try {
            const response = await axios.post('/api/director/addNotice', notice, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('accessToken')}` // 토큰을 헤더에 추가
                }
            });

            window.location.href = '/director/main';

//            if (response.ok) {
//                window.location.href = '/director/main';
//            } else {
//                alert('공지사항 추가에 실패했습니다');
//            }
        } catch (error) {
            console.error('Error:', error);
            alert('공지사항 추가 중 오류가 발생했습니다');
        }
    }

    const goBackToAdminNotice = () => {
        window.location.href = '/director/main';
    }

    return (
        <div id="App">
            <div id="director_noticewrite_frame">
                <div id="director_noticewrite_header">
                    <a onClick={goBackToAdminNotice}> &lt; </a>
                    <h2>공지사항 등록</h2>
                    <div id="write_btn">
                        <div id="write" onClick={handleSubmit}>등록</div>
                    </div>
                </div>
                <div id="rect"/>
                <div id="director_noticewrite_body">
                    <div>
                        <label htmlFor="title">제목:</label>
                        <input
                            type="text"
                            id="title"
                            value={title}
                            onChange={handleTitleChange}
                        />
                    </div>
                    <div>
                        <label htmlFor="content">내용:</label>
                        <textarea
                            id="content"
                            value={content}
                            onChange={handleContentChange}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DirectorNoticeWrite;
