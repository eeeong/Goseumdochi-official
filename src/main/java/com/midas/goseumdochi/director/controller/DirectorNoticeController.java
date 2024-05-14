package com.midas.goseumdochi.director.controller;

import com.midas.goseumdochi.director.dto.DirectorNoticeDTO;
import com.midas.goseumdochi.director.entity.DirectorNoticeEntity;
import com.midas.goseumdochi.director.repository.DirectorNoticeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/director")
public class DirectorNoticeController {

    private final DirectorNoticeRepository noticeRepository;

    @Autowired
    public DirectorNoticeController(DirectorNoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Autowired
    private HttpSession httpSession;

    private boolean isLoggedIn() {
        return httpSession.getAttribute("directorId") != null;
    }

    @GetMapping("/noticeList")
    public ResponseEntity<List<DirectorNoticeEntity>> showNoticeList() {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<DirectorNoticeEntity> noticeEntities = noticeRepository.findAll();
        return ResponseEntity.ok(noticeEntities);
    }

    @PostMapping("/addNotice")
    public ResponseEntity<String> addNotice(@RequestBody DirectorNoticeDTO noticeDTO) {
        if (!isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 현재 로그인된 원장님의 ID를 작성자로 저장
        String writer = (String) httpSession.getAttribute("directorId");

        // 현재 날짜를 생성 후 공지사항에 등록
        Date regdate = new Date(System.currentTimeMillis());

        // 공지사항을 저장
        DirectorNoticeEntity notice = new DirectorNoticeEntity();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        notice.setRegdate(regdate);
        notice.setWriter(writer);

        noticeRepository.save(notice);

        return ResponseEntity.ok("공지사항이 추가되었습니다");
    }
}
