import '../css/community.css';
import logo from './images/goseumdochi.png';

function App24() {
return (
        <div id="App">
            <div id="community_header">
                <div id="menu_btn"/>
                <div id="home_btn"/>
                <div id="title">
                    <img src={logo}/>
                    <h2>고슴도치 커뮤니티</h2>
                </div>
            </div>
            <div id="menu_community">
                <ul>
                    <li><a>자유 게시판</a></li>
                    <li><a>HOT 게시물</a></li>
                    <li><a>대입 게시판</a></li>
                    <li><a>질문 게시판</a></li>
                    <li><a>대학 입결 정보</a></li>
                    <li><a>학원 리뷰</a></li>
                    <li><a>마이페이지</a></li>
                </ul>
            </div>
            <div id="contents_community">
                <div id="header_contents_community">
                    <div id="findsomething">
                        <input type="text" placeholder="제목" id="find_title"/>
                        <input type="submit" value="검색" id="find_btn"/>
                        <div id="write_btn">
                            <a>글쓰기</a>
                        </div>
                    </div>
                </div>
                <div id="contents_contents_community">
                    <div id="letter_contents_community">

                    </div>
                </div>
                <div id="footer_contents_community">
                    <div id="back_write_btn">
                        <a>이전</a>
                    </div>
                    <div id="next_write_btn">
                        <a>다음</a>
                    </div>
                    <div id="page_number">
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App24;