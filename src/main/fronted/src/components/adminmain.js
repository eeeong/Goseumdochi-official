import '../css/adminmain.css';
import logo from './images/goseumdochi.png';

function App18() {
    return (
        <div id="App">
            <div id="header_adminmain">
                <img src={logo}/>
            </div>
            <div id="admin_info">
                <div id="info">
                    <a>관리자 : ㅇㅇㅇ 님</a>
                    <div id="logout">
                        로그아웃
                    </div>
                </div>
            </div>
            <div id="button_adminmain">
                <ul>
                    <li><a>전체 공지사항</a></li>
                    <li><a>학원 신청서</a></li>
                    <li><a>학원 관리</a></li>
                    <li><a>학생 관리</a></li>
                    <li><a>커뮤니티</a></li>
                </ul>
            </div>
        </div>
    );
}

export default App18;