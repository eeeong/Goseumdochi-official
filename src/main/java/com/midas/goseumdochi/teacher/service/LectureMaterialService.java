package com.midas.goseumdochi.teacher.service;

import com.midas.goseumdochi.teacher.dto.LectureMaterialDTO;
import com.midas.goseumdochi.teacher.entity.LectureMaterialEntity;
import com.midas.goseumdochi.teacher.repository.LectureMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureMaterialService {
    private final LectureMaterialRepository lectureMaterialRepository;

    // 새로운 강의 자료를 저장
    public void saveLectureMaterial(LectureMaterialDTO lectureMaterialDTO) {
        LectureMaterialEntity entity = LectureMaterialEntity.builder()
                .title(lectureMaterialDTO.getTitle())
                .content(lectureMaterialDTO.getContent())
                .author(lectureMaterialDTO.getAuthor())
                .createdAt(LocalDateTime.now()) // 현재 시각을 설정
                .attachmentPath(lectureMaterialDTO.getAttachmentPath())
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

    // 특정 강의 자료를 업데이트
    public void updateLectureMaterial(Long id, LectureMaterialDTO lectureMaterialDTO) {
        LectureMaterialEntity entity = lectureMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("첨부파일이 존재하지 않습니다."));
        entity.setTitle(lectureMaterialDTO.getTitle());
        entity.setContent(lectureMaterialDTO.getContent());
        entity.setAttachmentPath(lectureMaterialDTO.getAttachmentPath());
        lectureMaterialRepository.save(entity);
    }
}
