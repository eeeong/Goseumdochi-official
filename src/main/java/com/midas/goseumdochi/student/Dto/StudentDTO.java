package com.midas.goseumdochi.student.Dto;
import com.midas.goseumdochi.student.entity.StudentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentDTO {
    private Long id;
    
    private String studentId;

    private String studentPassword;

    private String studentName;

    private String studentBirthDate;

    private String studentPhoneNumber;

    private String studentEmail;

    private String profilePictureUrl;
    

    public static StudentDTO toStudentDTO(StudentEntity studentEntity){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setStudentId(studentEntity.getStudentId());
        studentDTO.setStudentPassword(studentEntity.getStudentPassword());
        studentDTO.setStudentName(studentEntity.getStudentName());
        studentDTO.setStudentBirthDate(studentEntity.getStudentBirthDate());
        studentDTO.setStudentPhoneNumber(studentEntity.getStudentPhoneNumber());
        studentDTO.setStudentEmail(studentEntity.getStudentEmail());
        studentDTO.setProfilePictureUrl(studentEntity.getProfilePictureUrl());
        return studentDTO;
    }
}
