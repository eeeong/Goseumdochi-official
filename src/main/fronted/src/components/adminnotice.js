import '../css/adminnotice.css';
import logo from './images/goseumdochi.png';

function App19() {
return (
        <div id="App">
            <div id="adminnotice_frame">
                <div id="adminnotice_header">
                    <a> > </a>
                    <h2>전체 공지사항</h2>
                    <div id="write_btn">
                        <div id="write">글쓰기</div>
                    </div>
                </div>
                <div id="rect"/>
                <div id="adminnotice_body">
                    <div id="adminnotice_category">
                        <div id="num">
                            번호
                        </div>
                        <div id="title">
                            제목
                        </div>
                        <div id="postperson">
                            게시자
                        </div>
                        <div id="postdate">
                            게시일
                        </div>
                        <div id="delete">
                            글 삭제
                        </div>
                    </div>
                    <div id="adminnotice_contents">

                    </div>
                </div>
            </div>
        </div>
    );
}

export default App19;