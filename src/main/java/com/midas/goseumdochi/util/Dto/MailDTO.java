package com.midas.goseumdochi.util.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailDTO {
    private String address; // 이메일 받는 사람 주소
    private String title;   // 이메일 제목
    private String message; // 이메일 메시지 본문
}