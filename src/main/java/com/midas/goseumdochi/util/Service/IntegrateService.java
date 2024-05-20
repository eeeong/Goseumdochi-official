package com.midas.goseumdochi.util.Service;

import com.midas.goseumdochi.director.entity.DirectorEntity;
import com.midas.goseumdochi.director.repository.DirectorRepository;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import com.midas.goseumdochi.student.entity.StudentEntity;
import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import com.midas.goseumdochi.teacher.repository.TeacherRepository;
import com.midas.goseumdochi.util.Dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntegrateService {
    private final DirectorRepository directorRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    // 통합 로그인 (성공: UserDTO 리턴, 실패: 실패 메시지 리턴)
    public ResponseEntity<?> login(String loginid, String password) {
        // 원장 로그인
        Optional<DirectorEntity> findDirector = directorRepository.findByLoginid(loginid);
        if (findDirector.isPresent()) { // 로그인id으로 찾음
            if (findDirector.get().getPassword().equals(password)) // 비밀번호 일치 -> 원장 로그인 성공
                return ResponseEntity.ok(new UserDTO(findDirector.get().getId(), "director"));
            else // 비밀번호 불일치
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("원장님 비밀번호가 일치하지 않습니다.");
        }

        // 선생 로그인
        Optional<TeacherEntity> findTeacher = teacherRepository.findByLoginid(loginid);
        if (findTeacher.isPresent()) {
            if (findTeacher.get().getPassword().equals(password)) // 로그인 성공
                return ResponseEntity.ok(new UserDTO(findTeacher.get().getId(), "teacher"));
            else // 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("선생님 비밀번호가 일치하지 않습니다.");
        }

        // 학생 로그인
        Optional<StudentEntity> findStudent = studentRepository.findByStudentId(loginid);
        if (findStudent.isPresent()) {
            if (findStudent.get().getStudentPassword().equals(password)) // 로그인 성공
                return ResponseEntity.ok(new UserDTO(findStudent.get().getId(), "student"));
            else // 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("학생 비밀번호가 일치하지 않습니다.");
        }

        // 로그인 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 존재하지 않습니다.");
    }
}
