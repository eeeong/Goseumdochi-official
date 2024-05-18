package com.midas.goseumdochi.director.controller;

import com.midas.goseumdochi.director.dto.DirectorDTO;
import com.midas.goseumdochi.academy.dto.StudentAcademyDTO;
import com.midas.goseumdochi.academy.service.StudentAcademyService;
import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.midas.goseumdochi.director.service.DirectorService;


@RestController
@RequestMapping("/api/director")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;
    private final StudentService studentService;
    private final StudentAcademyService studentDirectorService;

    // 로그인 폼 작성 후
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String directorLoginid, @RequestParam String directorPassword) {
        DirectorDTO directorDTO = directorService.findDirector(directorLoginid, directorPassword);

        if(directorDTO == null) { // 로그인 실패
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("원장 로그인 실패");
        }
        // 로그인 성공
        return ResponseEntity.ok(directorDTO.getId()); // 원장 id 리턴
    }

    // 원장님이 학생 찾을 때 (이름, 전화번호 이용) *--> 학생 Controller에 보내도 됨
    @PostMapping("/findStudent")
    public ResponseEntity<?> findStudent(@RequestParam String studentName, @RequestParam String studentPhoneNumber) {
        StudentDTO studentDTO = studentService.findStudentByNameAndPhoneNumber(studentName, studentPhoneNumber);

        if(studentDTO == null) { // 찾기 실패
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("해당 학생이 존재하지 않습니다.");
        }
        // 학생 찾기 성공
        return ResponseEntity.ok(studentDTO); // -> 리액트에서 찾기 성공했을 때 변수 설정 해줘야함
    }

    // 학생 등록
    @PostMapping("/registStudent")
    public ResponseEntity<?> registStudent(@RequestBody StudentAcademyDTO studentAcademyDTO) { // (학생id, 학원id) 입력받음
        // 학생 등록 실패 로직
        if(studentAcademyDTO.getStudentId() == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("학생 등록 실패. 학생을 선택해주세요!");
        }
        // 학생 등록 성공
        studentDirectorService.registStudentAcademy(studentAcademyDTO); // 등록
        return ResponseEntity.ok("학생 등록 성공.");
    }
}