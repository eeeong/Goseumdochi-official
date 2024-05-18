package com.midas.goseumdochi.academy.entity;

import com.midas.goseumdochi.academy.dto.AcademyFormDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "academy_form")
public class AcademyFormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aca_form_id")
    private Long id;

    @Column(name = "aca_form_director_name", length = 10)
    private String directorName;

    @Column(name = "aca_form_director_phonno", length = 11)
    private String directorPhoneNumber;

    @Column(name = "aca_form_director_email", length = 64)
    private String directorEmail;

    @Column(name = "aca_form_aca_name", length = 32)
    private String academyName;

    @Column(name = "aca_form_aca_phoneno", length = 13)
    private String academyPhoneNumber;

    @Column(name = "aca_form_aca_postcode", length = 5)
    private String academyPostcode;

    @Column(name = "aca_form_aca_address", length = 64)
    private String academyAddress;

    @Column(name = "aca_form_aca_address_detail", length = 64)
    private String academyAddressDetail;

    @Column(name = "aca_form_auth_status")
    @ColumnDefault("0") // 기본 0: 대기, 1: 수락, -1: 실패
    private int authStatus; // 신청서 인증여부

    @Builder
    public AcademyFormEntity(long id, String directorName, String directorPhoneNumber, String directorEmail
            , String academyName, String academyPhoneNumber, String academyPostcode, String academyAddress
            , String academyAddressDetail, int authStatus) {
        this.id = id;
        this.directorName = directorName;
        this.directorPhoneNumber = directorPhoneNumber;
        this.directorEmail = directorEmail;
        this.academyName = academyName;
        this.academyPhoneNumber = academyPhoneNumber;
        this.academyPostcode = academyPostcode;
        this.academyAddress = academyAddress;
        this.academyAddressDetail = academyAddressDetail;
        this.authStatus = authStatus;
    }

    // AcademyFormDTO -> AcademyFormEntity
    public static AcademyFormEntity toAcademyFormEntity(AcademyFormDTO academyFormDTO) {
        AcademyFormEntity academyFormEntity = AcademyFormEntity.builder()
                .id(academyFormDTO.getId())
                .directorName(academyFormDTO.getDirectorName())
                .directorPhoneNumber(academyFormDTO.getDirectorPhoneNumber())
                .directorEmail(academyFormDTO.getDirectorEmail())
                .academyName(academyFormDTO.getAcademyName())
                .academyPhoneNumber(academyFormDTO.getAcademyPhoneNumber())
                .academyPostcode(academyFormDTO.getAcademyPostcode())
                .academyAddress(academyFormDTO.getAcademyAddress())
                .academyAddressDetail(academyFormDTO.getAcademyAddressDetail())
                .authStatus(academyFormDTO.getAuthStatus())
                .build();

        return academyFormEntity;
    }

    // 상태 변경하려고 만듬 (하은)
    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }
}
