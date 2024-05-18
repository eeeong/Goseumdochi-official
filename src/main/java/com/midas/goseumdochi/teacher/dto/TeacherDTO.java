package com.midas.goseumdochi.teacher.dto;

import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherDTO { // 원장이랑 일단 똑같음
    private Long id;
    private String name;
    private String loginid;
    private String password;
    private String phoneNumber;
    private LocalDate birthdate;
    private String email;
    private Long academyId; // fk

    // TeacherEntity -> TeacherDTO
    public static TeacherDTO toTeacherDTO(TeacherEntity teacherEntity) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacherEntity.getId());
        teacherDTO.setName(teacherEntity.getName());
        teacherDTO.setLoginid(teacherEntity.getLoginid());
        teacherDTO.setPassword(teacherEntity.getPassword());
        teacherDTO.setBirthdate(teacherEntity.getBirthdate());
        teacherDTO.setEmail(teacherEntity.getEmail());
        teacherDTO.setAcademyId(teacherEntity.getAcademyEntity().getId()); // fk

        return teacherDTO;
    }
}
