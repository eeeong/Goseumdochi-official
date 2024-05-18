package com.midas.goseumdochi.admin.controller;

import com.midas.goseumdochi.admin.dto.AdminNoticeDTO;
import com.midas.goseumdochi.admin.entity.AdminNoticeEntity;
import com.midas.goseumdochi.admin.repository.AdminNoticeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminNoticeController {

    private final AdminNoticeRepository noticeRepository;

    @Autowired
    public AdminNoticeController(AdminNoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Autowired
    private HttpSession httpSession;

    private boolean isLoggedIn() {
        return httpSession.getAttribute("adminId") != null;
    }

    @GetMapping("/noticeList")
    public ResponseEntity<List<AdminNoticeEntity>> showNoticeList() {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<AdminNoticeEntity> noticeEntities = noticeRepository.findAll();
        return ResponseEntity.ok(noticeEntities);
    }

    @PostMapping("/addNotice")
    public ResponseEntity<String> addNotice(@RequestBody AdminNoticeDTO noticeDTO) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 현재 로그인된 관리자의 ID를 작성자로 저장
        String writer = (String) httpSession.getAttribute("adminId");

        // 현재 날짜를 생성 후 공지사항에 등록
        Date regdate = new Date(System.currentTimeMillis());

        // 공지사항을 저장
        AdminNoticeEntity notice = new AdminNoticeEntity();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        notice.setRegdate(regdate);
        notice.setWriter(writer);

        noticeRepository.save(notice);

        return ResponseEntity.ok("공지사항이 추가되었습니다");
    }
}
