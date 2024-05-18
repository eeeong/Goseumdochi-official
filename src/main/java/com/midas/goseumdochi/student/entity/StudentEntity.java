package com.midas.goseumdochi.student.entity;
import com.midas.goseumdochi.academy.entity.StudentAcademyEntity;
import com.midas.goseumdochi.student.Dto.StudentDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "students")
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String studentId;

    @Column
    private String studentPassword;

    @Column
    private String studentName;

    @Column
    private String studentBirthDate;

    @Column
    private String studentPhoneNumber;

    @Column
    private String studentEmail;

    @Column
    private String profilePictureUrl;

    // 관계 엔터티와 1:N 연결 (원장과 N:M 연결을 위해)
    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAcademyEntity> studentAcademyEntityList;

    public static StudentEntity toStudent(StudentDTO studentDTO){
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(studentDTO.getId());
        studentEntity.setStudentId(studentDTO.getStudentId());
        studentEntity.setStudentPassword(studentDTO.getStudentPassword());
        studentEntity.setStudentName(studentDTO.getStudentName());
        studentEntity.setStudentBirthDate(studentDTO.getStudentBirthDate());
        studentEntity.setStudentPhoneNumber(studentDTO.getStudentPhoneNumber());
        studentEntity.setStudentEmail(studentDTO.getStudentEmail());
        studentEntity.setProfilePictureUrl(studentDTO.getProfilePictureUrl());
        return studentEntity;
    }
}
