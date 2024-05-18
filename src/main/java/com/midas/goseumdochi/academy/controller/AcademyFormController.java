package com.midas.goseumdochi.academy.controller;

import com.midas.goseumdochi.academy.dto.AcademyFormDTO;
import com.midas.goseumdochi.academy.service.AcademyFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 리액트 받는 컨트롤러는 RestController 인가바
//@CrossOrigin // react와 spring을 연결 시킬 수 있다. API를 연결해준다
@RequestMapping("/api/academy/form")
@RequiredArgsConstructor
public class AcademyFormController {
    private final AcademyFormService academyFormService;

    // 학원 신청서 폼 작성 후
    @PostMapping()
    public ResponseEntity<?> submitAcademyForm(@RequestBody AcademyFormDTO academyFormDTO) {
        // 신청서 이미 있으면 중복처리
        AcademyFormDTO findAcademyForm = academyFormService.findAcademyForm(academyFormDTO.getDirectorName(),
                academyFormDTO.getDirectorPhoneNumber());

        if(findAcademyForm != null) { // 신청서 제출 실패
            // 로그인 실패 시, ResponseEntity에 에러 메시지와 함께 401 상태 코드를 반환
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) // 에러 상태
                    .body("신청서 등록 실패. 중복된 원장 정보입니다."); // 팝업 띄울 메시지 리턴
        }
        // 성공
        academyFormService.submit(academyFormDTO);
        return ResponseEntity.ok(academyFormDTO); // LoginDto 객체를 담아서 반환
        //** id는 비어있다
    }

    // 학원 신청서 찾기 작성 후
    @PostMapping("/find")
    public ResponseEntity<?> openAcademyForm(@RequestBody AcademyFormDTO academyFormDTO) {
        // 클라이언트에게 원장 이름, 원장 비밀번호 입력받았음
        AcademyFormDTO findAcademyFormDTO = academyFormService.findAcademyForm(academyFormDTO.getDirectorName()
                , academyFormDTO.getDirectorPhoneNumber());

        if(findAcademyFormDTO == null) { // 찾기 실패 (존재하지 않는 원장)
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("존재하지 않는 원장 정보입니다.");
        }
        // 찾기 성공
        return ResponseEntity.ok(findAcademyFormDTO);
    }

    // 학원 신청서 수정 작성 후
    @PostMapping("/update")
    public ResponseEntity<?> updateAcademyForm(@RequestBody AcademyFormDTO academyFormDTO) {
        int resultCheck = academyFormService.checkUpdate(academyFormDTO);

        if(resultCheck == 0) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("중복된 원장 정보입니다.");
        }
        // 성공
        academyFormService.update(academyFormDTO);
        return ResponseEntity.ok(academyFormDTO);
    }
}