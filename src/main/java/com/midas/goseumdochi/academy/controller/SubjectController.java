package com.midas.goseumdochi.academy.controller;

import com.midas.goseumdochi.academy.dto.SubjectDTO;
import com.midas.goseumdochi.academy.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/regist")
    public ResponseEntity<?> registSubject(@RequestBody SubjectDTO subjectDTO) { // (이름, 학원 id) 전달
        SubjectDTO findSubjectDTO = subjectService.findByNameAndAcademyId(subjectDTO.getName(), subjectDTO.getAcademyId());

        if(findSubjectDTO != null) { // 중복된 과목이 존재
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) // 에러 상태
                    .body("과목 등록 실패. 중복된 과목 입니다.");
        }

        // 과목 등록 성공
        subjectService.regist(subjectDTO);
        return ResponseEntity.ok(subjectDTO);
    }
}
