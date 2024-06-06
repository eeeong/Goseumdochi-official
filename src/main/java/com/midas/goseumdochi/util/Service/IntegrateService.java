package com.midas.goseumdochi.util.Service;

import com.midas.goseumdochi.academy.entity.StudentAcademyEntity;
import com.midas.goseumdochi.director.entity.DirectorEntity;
import com.midas.goseumdochi.director.repository.DirectorRepository;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import com.midas.goseumdochi.student.entity.StudentEntity;
import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import com.midas.goseumdochi.teacher.repository.TeacherRepository;
import com.midas.goseumdochi.util.Dto.UserDTO;
import com.midas.goseumdochi.util.ai.EncDecService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntegrateService {
    private final DirectorRepository directorRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EncDecService encDecService;

    // 통합 로그인 (성공: UserDTO 리턴, 실패: 실패 메시지 리턴)
    public ResponseEntity<?> login(String loginid, String password, HttpSession session) {
        // 원장 로그인
        Optional<DirectorEntity> findDirector = directorRepository.findByLoginid(loginid);
        if (findDirector.isPresent()) { // 로그인id으로 찾음
            if (encDecService.decrypt(findDirector.get().getPassword()).equals(password)) // 비밀번호 일치 -> 원장 로그인 성공
                return ResponseEntity.ok(new UserDTO(findDirector.get().getId(), "director"
                        , findDirector.get().getAcademyEntity().getId()));
            else // 비밀번호 불일치
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("원장님 비밀번호가 일치하지 않습니다.");
        }

        // 선생 로그인
        Optional<TeacherEntity> findTeacher = teacherRepository.findByLoginid(loginid);
        if (findTeacher.isPresent()) {
            if (encDecService.decrypt(findTeacher.get().getPassword()).equals(password)) {
                // 로그인 성공 시 세션에 정보 저장
                session.setAttribute("loginTeacherId", findTeacher.get().getLoginid());
                session.setAttribute("loginId", findTeacher.get().getId());
                session.setAttribute("teacherName", findTeacher.get().getName());

                return ResponseEntity.ok(new UserDTO(findTeacher.get().getId(), "teacher"
                        , findTeacher.get().getAcademyEntity().getId()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("선생님 비밀번호가 일치하지 않습니다.");
            }
        }

        // 학생 로그인
        Optional<StudentEntity> findStudent = studentRepository.findByStudentId(loginid);
        if (findStudent.isPresent()) {
            if (encDecService.decrypt(findStudent.get().getStudentPassword()).equals(password)) { // 로그인 성공
                // 세션저장
                session.setAttribute("loginstudentId", findStudent.get().getStudentId());
                session.setAttribute("loginId", findStudent.get().getId());
                session.setAttribute("studentName", findStudent.get().getStudentName());

                List<Long> academyIdList = new ArrayList<>();
                for (StudentAcademyEntity entity : findStudent.get().getStudentAcademyEntityList())
                    academyIdList.add(entity.getAcademyEntity().getId()); // 학생은 학원 id 리스트 저장
                return ResponseEntity.ok(new UserDTO(findStudent.get().getId(), "student", academyIdList));
            }
            else // 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("학생 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 존재하지 않습니다.");
    }
}
