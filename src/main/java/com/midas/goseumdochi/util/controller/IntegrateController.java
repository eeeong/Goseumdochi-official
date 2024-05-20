package com.midas.goseumdochi.util.controller;

import com.midas.goseumdochi.util.Service.IntegrateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/integrate")
@RequiredArgsConstructor
public class IntegrateController {
    private final IntegrateService integrateService;

    // 통합 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String loginid, @RequestParam String password) {
        return integrateService.login(loginid, password); // (성공: UserDTO 리턴, 실패: 실패 메시지 리턴)
    }
}
