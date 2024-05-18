package com.midas.goseumdochi.teacher.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LectureMaterialDTO {
    private Long id;
    private String title;   // 제목
    private String content; // 내용
    private String author;  // 글쓴이
    private LocalDateTime createdAt;    // 생성일시
    private String attachmentPath;  // 첨부파일 경로
}