import '../css/lectureportal.css';
import logo from './images/goseumdochi.png';

function App10() {
    const Gomain=()=>{
        window.location.href='/main'
    }

    const Gonotice=()=>{
        window.location.href='/notice'
    }

    const Gomypage=()=>{
        window.location.href='/mypage'
    }

    return (
        <div id="App">
            <div id="lectureportal-menu">
                <div id="header_lectureportal">
                    <img src={logo} onClick={Gomain}/>
                    <div id="user_info"></div>
                </div>
                <div id="buttons_lectureportal">
                    <input type="submit" value="공지사항" id="notice_btn" onClick={Gonotice}/>
                    <input type="submit" value="커뮤니티" id="community_btn"/>
                    <input type="submit" value="마이페이지" id="mypage_btn" onClick={Gomypage}/>
                    <div id="rect"/>
                </div>
                <div id="contents_lectureportal">
                    <div id="aboutNotice_lectureportal">
                        <div id="category_aboutNotice_lectureportal">
                            <ul>
                                <li><a>공지사항</a></li>
                                <li><a>강의자료</a></li>
                                <li><a>온라인강의</a></li>
                                <li><a>과제</a></li>
                                <li><a>시험</a></li>
                            </ul>
                        </div>
                        <div id="contents_aboutNotice_lectureportal">
                            <div id="category_contents_lectureportal">
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
                                <div id="visit">
                                    조회수
                                </div>
                            </div>
                            <div id="body_contents_lectureportal">

                            </div>
                        </div>
                    </div>
                </div>
                <div id="footer_lectureportal">
                    <a>문의 | midas2024.ver01@gmail.com</a>
                </div>
            </div>
        </div>
    );
}

export default App10;