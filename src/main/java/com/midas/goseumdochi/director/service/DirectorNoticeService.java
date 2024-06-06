package com.midas.goseumdochi.director.service;

import com.midas.goseumdochi.director.dto.DirectorNoticeDTO;
import com.midas.goseumdochi.director.entity.DirectorNoticeEntity;
import com.midas.goseumdochi.director.repository.DirectorNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorNoticeService {
    private final DirectorNoticeRepository directorNoticeRepository;

    // 원장 공지사항 페이징
    public Page<DirectorNoticeDTO> pagingNotice(Long directorid, Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // 서버에서 페이지를 처리할 땐 0부터 시작해서
        int pageLimit = 5; // 한 페이지에 보여줄 글 개수 (ex. 글1 글2 글3)
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // PageRequest.of: Pageable 객체 생성 page: 페이지 인덱스, pageLimit: 한 페이지당 보여줄 글 개수, sort: "num" 실제 entity 필드명으로 정렬
        Page<DirectorNoticeEntity> directorNoticeEntityPage = directorNoticeRepository.findAllByDirectorId(directorid
                , PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "num")));

        System.out.println("boardEntities.getContent() = " + directorNoticeEntityPage.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + directorNoticeEntityPage.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + directorNoticeEntityPage.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + directorNoticeEntityPage.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + directorNoticeEntityPage.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + directorNoticeEntityPage.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + directorNoticeEntityPage.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + directorNoticeEntityPage.isLast()); // 마지막 페이지 여부

        // 이렇게 page로 map해서 리턴하면 위의 페이지 관련 변수도 전달됨
        Page<DirectorNoticeDTO> directorNoticeDTOPage = directorNoticeEntityPage.map(entity -> new DirectorNoticeDTO(
                entity.getNum(), entity.getTitle(), entity.getContent(), entity.getRegdate()));

        return directorNoticeDTOPage;
    }
}
