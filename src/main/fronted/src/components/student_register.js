import '../css/student_register.css';
import logo from './images/goseumdochi.png';

function App14() {

    return (
        <div id="App">
            <div id="blank_student_register"/>
            <div id="write_student_register">
                <div id="header_student_register">
                    <input type="submit" value="<" id="back_btn"/>
                </div>
                <div id="findinfo_student_register">
                    <a>학생 이름</a>
                    <input type="text" placeholder="이름" id="name"/>
                    <hr/>
                    <a>전화 번호</a>
                    <input type="text" placeholder="000-0000-0000" id="phonenum"/>
                    <input type="submit" value="찾기" id="find_btn"/>
                </div>
                <div id="selectinfo_student_register">
                    <div id="foundinfo"/>
                </div>
                <div id="button_student_register">
                    <input type="submit" value="등록" id="submit_btn"/>
                </div>
            </div>
            <div id="footer_student_register">
                <a>문의 | midas2024.ver01@gmail.com</a>
            </div>
        </div>
    );
}

export default App14;