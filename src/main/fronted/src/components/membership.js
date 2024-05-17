import '../css/membership.css';
import logo from './images/goseumdochi.png';

function App2() {
    return (
        <div id="App">
            <div id="header_membership"/>
            <div id="membership_write">
                <div id="membership_write_header">
                    <img src={logo}/>
                    <a>회원가입</a>
                </div>
                <div id="membership_write_buttons">
                    <input type="text" placeholder="아이디"/><hr/>
                    <input type="password" placeholder="비밀번호"/><hr/>
                    <input type="password" placeholder="비밀번호 확인"/><hr/>
                    <input type="name" placeholder="이름"/><hr/>
                    <input type="date" placeholder="출생연도"/><hr/>
                    <input type="phonenumber" placeholder="010-0000-0000"/><hr/>
                    <input type="text" placeholder="인증번호"/><hr/>
                    <input type="submit" value="회원가입" id="create_btn"/>
                </div>
            </div>
            <div id="footer_membership"/>
        </div>
    );
}

export default App2;