import '../css/community.css';
import logo from './images/goseumdochi.png';

function App24() {
return (
        <div id="App">
            <div id="community_header">
                <div id="menu_btn"/>
                <div id="title">
                    <img src={logo}/>
                    <h2>고슴도치 커뮤니티</h2>
                </div>
                <div id="home_btn"/>
            </div>
            <div id="menu_community"
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
                </div>
                <div id="contents_contents_community">
                </div>
                <div id="footer_contents_community">
                </div>
            </div>
        </div>
    );
}

export default App24;