package com.midas.goseumdochi.director.controller;

import com.midas.goseumdochi.director.dto.DirectorNoticeDTO;
import com.midas.goseumdochi.director.entity.DirectorNoticeEntity;
import com.midas.goseumdochi.director.repository.DirectorNoticeRepository;
import com.midas.goseumdochi.director.repository.DirectorRepository;
import com.midas.goseumdochi.director.service.DirectorNoticeService;
import com.midas.goseumdochi.util.component.PageComponent;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Pageable import 경로 중요
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/director")
public class DirectorNoticeController {

    private final DirectorNoticeRepository noticeRepository;
    private final DirectorRepository directorRepository;
    private final DirectorNoticeService directorNoticeService;
    private final PageComponent pageComponent;

    @Autowired
    public DirectorNoticeController(DirectorNoticeRepository noticeRepository, DirectorRepository directorRepository, DirectorNoticeService directorNoticeService, PageComponent pageComponent) {
        this.noticeRepository = noticeRepository;
        this.directorRepository = directorRepository;
        this.directorNoticeService = directorNoticeService;
        this.pageComponent = pageComponent;
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
//        if (!isLoggedIn()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

        // 현재 로그인된 원장님의 ID를 작성자로 저장
        String writer = (String) httpSession.getAttribute("directorId");

        // 현재 날짜를 생성 후 공지사항에 등록
        Date regdate = new Date(System.currentTimeMillis());

        // 공지사항을 저장
        DirectorNoticeEntity notice = new DirectorNoticeEntity();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        notice.setRegdate(regdate);
        notice.setDirectorEntity(directorRepository.findById(noticeDTO.getDirectorId()).get()); // 연관관계 추가해서

        noticeRepository.save(notice);

        return ResponseEntity.ok("공지사항이 추가되었습니다");
    }

    // 원장 공지사항 리스트 페이징
    @GetMapping("/{directorId}/notice/paging")
    public ResponseEntity<?> pagingNotice(@PathVariable Long directorId, @PageableDefault(page = 1) Pageable pageable) { // ?page: 페이지 번호
        Page<DirectorNoticeDTO> directorNoticeDTOPage = directorNoticeService.pagingNotice(directorId, pageable);
        int blockLimit = 3; // 화면에 보여줄 페이지 번호 개수 (ex. 1 2 3 4 5 6 7 8 9 10)
        int startPage = pageComponent.getStartPage(pageable.getPageNumber(), blockLimit);
        int endPage = pageComponent.getEndPage(startPage, blockLimit, directorNoticeDTOPage.getTotalPages());

        // Map: 리액트로 여러개의 변수를 보내기 위해
        Map<String, Object> response = new HashMap<>();
        response.put("directorNotice", directorNoticeDTOPage); // 엔터티 데이터는 content에 들어있음
        response.put("startPage", startPage);
        response.put("endPage", endPage);

        return ResponseEntity.ok(response);
    }
}