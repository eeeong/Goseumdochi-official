package com.midas.goseumdochi.academy.dto;

import com.midas.goseumdochi.academy.entity.AcademyFormEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AcademyFormDTO {
    private Long id;
    private String directorName;
    private String directorPhoneNumber;
    private String directorEmail;
    private String academyName;
    private String academyPhoneNumber;
    private String academyPostcode;
    private String academyAddress;
    private String academyAddressDetail;
    private int authStatus;
    private String authNumber; // 인증번호 DTO에는 필요

    // AcademyFormEntity -> AcademyFormDTO
    public static AcademyFormDTO toAcademyFormDTO(AcademyFormEntity academyFormEntity) {
        AcademyFormDTO academyFormDTO = new AcademyFormDTO();
        academyFormDTO.setId(academyFormEntity.getId());
        academyFormDTO.setDirectorName(academyFormEntity.getDirectorName());
        academyFormDTO.setDirectorPhoneNumber(academyFormEntity.getDirectorPhoneNumber());
        academyFormDTO.setDirectorEmail(academyFormEntity.getDirectorEmail());
        academyFormDTO.setAcademyName(academyFormEntity.getAcademyName());
        academyFormDTO.setAcademyPhoneNumber(academyFormEntity.getAcademyPhoneNumber());
        academyFormDTO.setAcademyPostcode(academyFormEntity.getAcademyPostcode());
        academyFormDTO.setAcademyAddress(academyFormEntity.getAcademyAddress());
        academyFormDTO.setAcademyAddressDetail(academyFormEntity.getAcademyAddressDetail());
        academyFormDTO.setAuthStatus(academyFormEntity.getAuthStatus()); // boolean 타입은 is~ 으로 getter 생성

        return academyFormDTO;
    }
}
