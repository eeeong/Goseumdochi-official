package com.midas.goseumdochi.teacher.service;

import com.midas.goseumdochi.teacher.dto.LectureMaterialDTO;
import com.midas.goseumdochi.teacher.entity.LectureEntity;
import com.midas.goseumdochi.teacher.entity.LectureMaterialEntity;
import com.midas.goseumdochi.teacher.repository.LectureMaterialRepository;
import com.midas.goseumdochi.teacher.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureMaterialService {
    private final LectureMaterialRepository lectureMaterialRepository;
    private final LectureRepository lectureRepository;

    // 새로운 강의 자료를 저장
    public void saveLectureMaterial(LectureMaterialDTO lectureMaterialDTO) {
        LectureMaterialEntity entity = LectureMaterialEntity.builder()
                .title(lectureMaterialDTO.getTitle())
                .content(lectureMaterialDTO.getContent())
                .author(lectureMaterialDTO.getAuthor())
                .createdAt(LocalDateTime.now())
                .attachmentPath(lectureMaterialDTO.getAttachmentPath())
                .lectureEntity(lectureRepository.findById(lectureMaterialDTO.getLectureId()).get())
                .build();
        lectureMaterialRepository.save(entity);
    }

    // 모든 강의 자료를 조회
    public List<LectureMaterialDTO> getAllLectureMaterials() {
        return lectureMaterialRepository.findAll().stream().map(entity -> new LectureMaterialDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor(),
                entity.getCreatedAt(),
                entity.getAttachmentPath()
        )).collect(Collectors.toList());
    }

    // 특정 강의 자료를 조회
    public LectureMaterialDTO getLectureMaterialById(Long id) {
        LectureMaterialEntity entity = lectureMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("첨부파일이 존재하지 않습니다."));
        return new LectureMaterialDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor(),
                entity.getCreatedAt(),
                entity.getAttachmentPath()
        );
    }

    // 특정 강의의 모든 강의 자료를 조회
    public List<LectureMaterialDTO> getMaterialsByLectureId(Long lectureId) {
        List<LectureMaterialEntity> entities = lectureMaterialRepository.findAllByLectureId(lectureId);
        return entities.stream().map(entity -> new LectureMaterialDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthor(),
                entity.getCreatedAt(),
                entity.getAttachmentPath()
        )).collect(Collectors.toList());
    }

    // 특정 강의 자료를 업데이트
    public void updateLectureMaterial(Long id, LectureMaterialDTO lectureMaterialDTO) {
        LectureMaterialEntity entity = lectureMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("첨부파일이 존재하지 않습니다."));
        entity.setTitle(lectureMaterialDTO.getTitle());
        entity.setContent(lectureMaterialDTO.getContent());
        entity.setAttachmentPath(lectureMaterialDTO.getAttachmentPath());
        lectureMaterialRepository.save(entity);
    }

    // 강의 자료 삭제
    public void deleteLectureMaterial(Long id) {
        if (!lectureMaterialRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 수업 자료가 존재하지 않습니다. ID: " + id);
        }
        lectureMaterialRepository.deleteById(id);
    }

    // 강의 자료 페이징
    public Page<LectureMaterialDTO> pagingMaterial(Long lectureId, Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;
        Page<LectureMaterialEntity> lectureEntityPage = lectureMaterialRepository.findAllByLectureId(lectureId,
                PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        Page<LectureMaterialDTO> lectureMaterialDTOPage = lectureEntityPage.map(entity -> new LectureMaterialDTO(
                entity.getId(), entity.getTitle(), entity.getContent(), entity.getAuthor(), entity.getCreatedAt(),
                entity.getAttachmentPath()));

        return lectureMaterialDTOPage;
    }
}
