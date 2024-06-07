import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import App1 from './components/start.js';
import App2 from './components/membership.js';
import App3 from './components/login.js';
import App4 from './components/academy.js';
import App5 from './components/afterlogin.js';
import App6 from './components/main.js';
import App7 from './components/academy_register.js';
import App8 from './components/findID.js';
import App9 from './components/findPW.js';
import App10 from './components/lectureportal.js';
import App11 from './components/newPW.js';
import App12 from './components/mypage.js';
import App13 from './components/academy_modify.js';
import App14 from './components/student_register.js';
import App15 from './components/subject_register.js';
import App16 from './components/teacher_register.js';
import App17 from './components/findacademyform.js';
import App18 from './components/adminmain.js';
import App19 from './components/adminnotice.js';
import App20 from './components/adminnotice_write.js';
import App21 from './components/adminacademyregister.js';
import App22 from './components/adminacademymanage.js';
import App23 from './components/adminstudentmanage.js';
import App24 from './components/community.js';
import App25 from './components/teachermain.js';
import App26 from './components/teacherportal.js';
import Yewon from './components/yewon/test';
import AdminLogin from './components/yewon/admin_login';
import IntegrateLogin from './components/yewon/integrate_login';
import AdminAcademyFormManage from './components/yewon/admin_academyForm_manage'
import AcademyFormUpdate from './components/yewon/academyForm_update'
import DirectorMain from './components/yewon/director_main'
import DirectorStudentManage from './components/yewon/director_student_manage'
import DirectorStudentRegist from './components/yewon/director_student_regist'
import DirectorTeacherManage from './components/yewon/director_teacher_manage'
import DirectorTeacherRegist from './components/yewon/director_teacher_regist'
import DirectorSubjectmanage from './components/yewon/director_subject_manage'
import DirectorSubjectRegist from './components/yewon/director_subject_regist'
import TeacherLectureManage from './components/yewon/teacher_lecture_manage'
import TeacherLectureRegist from './components/yewon/teacher_lecture_regist'
import TeacherLectureFind from './components/yewon/teacher_lecture_find'
import TeacherLectureStudentManage from './components/yewon/teacher_lecture_student_manage'
import LectureMaterialPaging from './components/yewon/lecture_material_paging'
import LectureAssignmentPaging from './components/yewon/lecture_assignment_paging'
import StudentRecommendUniv from './components/yewon/student_recommend_univ'
import { Component } from 'react';

import App40 from './components/community_admin';
import DirectorNoticeWrite from './components/director_notice_write';
import BadWordCheck from './components/community_badword_check';

class App extends Component{
  constructor(props){
    super(props)
    this.state={
      
    }
  }

  render(){
    return(
      <div id='App'>
        <BrowserRouter>
            <Routes>
              <Route path='/' element={<App1/>}/>
              <Route path='/membership' element={<App2/>}/>
              <Route path='/login' element={<App3/>}/>
              <Route path='/academy' element={<App4/>}/>
              <Route path='/afterlogin' element={<App5/>}/>
              <Route path='/main' element={<App6/>}/>
              <Route path='/academyform' element={<App7/>}/>
              <Route path='/findID' element={<App8/>}/>
              <Route path='/findPW' element={<App9/>}/>
              <Route path='/lectureportal' element={<App10/>}/>
              <Route path='/newPW' element={<App11/>}/>
              <Route path='/mypage' element={<App12/>}/>
              <Route path='/academymodify' element={<App13/>}/>
              <Route path='/studentregister' element={<App14/>}/>
              <Route path='/subjectregister' element={<App15/>}/>
              <Route path='/teacherregister' element={<App16/>}/>
              <Route path='/findacademyform' element={<App17/>}/>
              <Route path='/adminmain' element={<App18/>}/>
              <Route path='/adminnotice' element={<App19/>}/>
              <Route path='/adminnoticewrite' element={<App20/>}/>
              <Route path='/adminacademyregister' element={<App21/>}/>
              <Route path='/adminacademymanage' element={<App22/>}/>
              <Route path='/adminstudentmanage' element={<App23/>}/>
              <Route path='/community' element={<App24/>}/>
              <Route path='/teachermain' element={<App25/>}/>
              <Route path='/teacherportal' element={<App26/>}/>
              <Route path='/yewon' element={<Yewon/>}/>
              <Route path='/admin/login' element={<AdminLogin/>}/>
              <Route path='/integrate/login' element={<IntegrateLogin/>}/>
              <Route path='/admin/academy/form/manage' element={<AdminAcademyFormManage/>}/>
              <Route path='/academy/form/update' element={<AcademyFormUpdate/>}/>
              <Route path='/director/main' element={<DirectorMain/>}/>
              <Route path='/director/student/manage' element={<DirectorStudentManage/>}/>
              <Route path='/director/student/regist' element={<DirectorStudentRegist/>}/>
              <Route path='/director/teacher/manage' element={<DirectorTeacherManage/>}/>
              <Route path='/director/teacher/regist' element={<DirectorTeacherRegist/>}/>
              <Route path='/director/subject/manage' element={<DirectorSubjectmanage/>}/>
              <Route path='/director/subject/regist' element={<DirectorSubjectRegist/>}/>
              <Route path='/teacher/lecture/manage' element={<TeacherLectureManage/>}/>
              <Route path='/teacher/lecture/regist' element={<TeacherLectureRegist/>}/>
              <Route path='/teacher/lecture/find' element={<TeacherLectureFind/>}/>
              <Route path='/teacher/lecture/student/manage' element={<TeacherLectureStudentManage/>}/>
              <Route path='/lecture/material/paging' element={<LectureMaterialPaging/>}/>
              <Route path='/lecture/assignment/paging' element={<LectureAssignmentPaging/>}/>
              <Route path='/student/recommend/univ' element={<StudentRecommendUniv/>}/>
              <Route path='/community_admin' element={<App40/>}/>
              <Route path='/director_notice_write' element={<DirectorNoticeWrite/>}/>
              <Route path='/community_badword_check' element={<BadWordCheck/>}/>
            </Routes>
        </BrowserRouter> 
      </div>
    )
  }
}

export default App;
