package com.midas.goseumdochi.admin.controller;

import com.midas.goseumdochi.admin.dto.AdminDTO;
import com.midas.goseumdochi.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class LoginController {

    private final AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminDTO adminDTO) {
        String username = adminDTO.getId();
        String password = adminDTO.getPassword();

        if ("admin".equals(username) && "password".equals(password)) {
            return ResponseEntity.ok().build(); // 로그인 성공
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("어드민 로그인 실패"); // 로그인 실패
        }
    }
}
