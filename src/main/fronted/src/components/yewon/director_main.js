import '../../css/teachermain.css';
import logo from '../images/goseumdochi_moving.gif';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

function DirectorMain() {
    const location = useLocation();
    const navigate = useNavigate();

    const { user } = location.state || {}; // id, role 전달

    const GoSubjectManage=()=>{
        navigate('/director/subject/manage', { state: { user: user } })
    }

    const GoTeacherManage=()=>{
        navigate('/director/teacher/manage', { state: { user: user } })
    }

    const GoStudentManage=()=>{
        navigate('/director/student/manage', { state: { user: user } })
    }

    const GoNoticeWrite=()=>{
        console.log(user);
        navigate('/director_notice_write', { state: { user: user } })
    }

    return (
        <div id="App">
            <div id="teachermain-menu">
                <div id="header_teachermain">
                    <img src={logo}/>
                </div>
                <div id="buttons_teachermain">
                    <input type="submit" value="과목관리" id="lecture_btn" onClick={GoSubjectManage}/>
                    <input type="submit" value="선생관리" id="studentmanage_btn " onClick={GoTeacherManage}/>
                    <input type="submit" value="학생관리" id="mypage_btn" onClick={GoStudentManage}/>
                    <input type="submit" value="공지 작성" id="notice_write_btn" onClick={GoNoticeWrite}/>
                    <div id="rect"/>
                </div>
                <div id="contents_teachermain">
                    <div id="contents1_teachermain">
                        <div id="calendar">
                        </div>
                        <div id="lectureSchedule">
                        </div>
                    </div>
                    <div id="contents2_teachermain">

                    </div>
                </div>
                <div id="footer_teachermain">
                    <a>문의 | midas2024.ver01@gmail.com</a>
                </div>
            </div>
        </div>
    );
}

export default DirectorMain;