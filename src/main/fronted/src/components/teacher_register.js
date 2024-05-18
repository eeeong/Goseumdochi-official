import '../css/teacher_register.css';
import logo from './images/goseumdochi.png';

function App16() {

    return (
        <div id="App">
            <div id="blank_teacher_register"/>
            <div id="write_teacher_register">
                <div id="header_teacher_register">
                    <input type="submit" value="<" id="back_btn"/>
                </div>
                <div id="writeinfo_teacher_register">
                    <a>선생 정보 입력</a>
                    <div id="writeinfo">
                        <input type="text" placeholder="이름" id="name"/><hr/>
                        <input type="text" placeholder="000-0000-0000" id="phonenum"/><hr/>
                        <input type="date" placeholder="생일" id="birthdate"/><hr/>
                        <input type="text" placeholder="이메일" id="email"/><hr/>
                    </div>
                </div>
                <div id="button_teacher_register">
                    <input type="submit" value="등록" id="submit_btn"/>
                </div>
            </div>
            <div id="footer_teacher_register">
                <a>문의 | midas2024.ver01@gmail.com</a>
            </div>
        </div>
    );
}

export default App16;