package com.midas.goseumdochi.teacher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lecture_material")
public class LectureMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   // 제목
    private String content; // 내용
    private String author;  // 글쓴이
    private LocalDateTime createdAt;    // 생성일시
    private String attachmentPath;  // 첨부파일 경로
}
