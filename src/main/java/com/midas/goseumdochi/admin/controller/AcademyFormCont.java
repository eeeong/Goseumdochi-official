package com.midas.goseumdochi.admin.controller;

import com.midas.goseumdochi.admin.dto.AcademyFormDTO;
import com.midas.goseumdochi.admin.service.AcademyFormServ;
import com.midas.goseumdochi.academy.entity.AcademyFormEntity;
import com.midas.goseumdochi.academy.repository.AcademyFormRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AcademyFormCont {

    private final AcademyFormRepository academyFormRepository;
    private final AcademyFormServ academyFormServ;
    private final HttpSession httpSession;

    @Autowired
    public AcademyFormCont(AcademyFormRepository academyFormRepository, AcademyFormServ academyFormServ, HttpSession httpSession) {
        this.academyFormRepository = academyFormRepository;
        this.academyFormServ = academyFormServ;
        this.httpSession = httpSession;
    }

    private boolean isLoggedIn() {
        return httpSession.getAttribute("adminId") != null;
    }

    @GetMapping("/academyForms")
    public ResponseEntity<List<AcademyFormEntity>> getAllAcademyForms() {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<AcademyFormEntity> academyForms = academyFormRepository.findAll();
        return ResponseEntity.ok(academyForms);
    }

    @PostMapping("/accept") // 수락 버튼 눌렀을 때
    public ResponseEntity<String> acceptAcademyForm(@RequestBody AcademyFormDTO academyFormDTO) {
        Long academyFormId = academyFormDTO.getId();
        academyFormServ.acceptAcademyForm(academyFormId);
        return ResponseEntity.ok("수락되었습니다");
    }

    @PostMapping("/reject") // 거절 버튼 눌렀을 때
    public ResponseEntity<String> rejectAcademyForm(@RequestBody AcademyFormDTO academyFormDTO) {
        Long academyFormId = academyFormDTO.getId();
        academyFormServ.rejectAcademyForm(academyFormId);
        return ResponseEntity.ok("거절되었습니다");
    }
}
