import '../css/subject_register.css';
import logo from './images/goseumdochi.png';

function App15() {

    return (
        <div id="App">
            <div id="blank_subject_register"/>
            <div id="write_subject_register">
                <div id="header_subject_register">
                    <input type="submit" value="<" id="back_btn"/>
                </div>
                <div id="showsubject_subject_register">
                    <div id="foundsubject"/>
                </div>
                <div id="addsubject_subject_register">
                    <a>추가할 과목</a>
                    <input type="text" id="addnewsubject"/>
                    <input type="submit" value="등록" id="submit_btn"/>
                </div>
            </div>
            <div id="footer_subject_register">
                <a>문의 | midas2024.ver01@gmail.com</a>
            </div>
        </div>
    );
}

export default App15;