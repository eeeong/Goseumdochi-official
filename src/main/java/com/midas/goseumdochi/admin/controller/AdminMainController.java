package com.midas.goseumdochi.admin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminMainController {

    @Autowired
    private HttpSession httpSession;

    private boolean isLoggedIn() {
        // 세션에서 관리자 ID를 가져와서 확인 ㄱㄱ
        return httpSession.getAttribute("adminId") != null;
    }
}
