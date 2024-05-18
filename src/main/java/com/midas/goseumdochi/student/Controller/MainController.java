package com.midas.goseumdochi.student.Controller;

import com.midas.goseumdochi.student.entity.StudentEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {

    private final StudentRepository studentRepository;

    @Autowired
    public MainController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 회원 가입을 위한 엔티티를 받고 저장 후 결과 반환
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@ModelAttribute StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
        return ResponseEntity.ok("회원가입이 완료되었습니다. 로그인 해주세요.");
    }

    // 사용자의 마이 페이지 정보를 조회하여 반환
    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
        if (!studentEntity.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        StudentDTO studentDTO = StudentDTO.toStudentDTO(studentEntity.get());
        return ResponseEntity.ok(studentDTO);
    }
}
