package com.midas.goseumdochi.director.entity;

import com.midas.goseumdochi.academy.entity.AcademyEntity;
import com.midas.goseumdochi.director.dto.DirectorDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
//@Setter
//@DynamicInsert
@NoArgsConstructor // 기본 생성자
@Table(name = "director")
public class DirectorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private Long id;

    @Column(name = "director_name", length = 10) // VARCHAR(10)
    private String name;

    @Column(name = "director_loginid", length = 8)
    private String loginid; // 8자리 숫자 (자릿수마다 의마가 있어서 문자열로)

    @Column(name = "director_pwd", length = 20)
    private String password;

    @Column(name = "director_phoneno", length = 13)
    private String phoneNumber;

    @Column(name = "director_birthdate")
    private LocalDate birthdate;

    @Column(name = "director_email", length = 32)
    private String email;

    // 학원과 1:1 양방향 매핑(주 엔터티)
    @OneToOne
    @JoinColumn(name = "academy_id") // name: DB에 저장되는 이름, referencedColumnName 지정 안 하면 자동으로 pk와 매핑
    private AcademyEntity academyEntity;

    @Builder
    public DirectorEntity(Long id, String name, String loginid, String password, String phoneNumber, LocalDate birthdate, String email) {
        this.id = id;
        this.name = name;
        this.loginid = loginid;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
        this.email = email;
    }

    // directorDTO -> directorEntity
    public static DirectorEntity toDirectorEntity(DirectorDTO directorDTO) {
        DirectorEntity directorEntity = DirectorEntity.builder()
                .id(directorDTO.getId())
                .name(directorDTO.getName())
                .loginid(directorDTO.getLoginid())
                .password(directorDTO.getPassword())
                .phoneNumber(directorDTO.getPhoneNumber())
                .birthdate(directorDTO.getBirthdate())
                .email(directorDTO.getEmail())
                .build();

        return directorEntity;
    }
}
