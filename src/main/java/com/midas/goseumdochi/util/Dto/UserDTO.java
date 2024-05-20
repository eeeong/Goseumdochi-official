package com.midas.goseumdochi.util.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private long id; // 원장 or 선생 or 학생 id(pk) 저장
    private String role; // 역할 저장
}
