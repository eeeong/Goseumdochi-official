package com.midas.goseumdochi.student.Service;

import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import com.midas.goseumdochi.student.entity.StudentEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //회원가입 로직
    public int join(StudentDTO studentDTO, String passwordCheck) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String birthDateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        if (!studentDTO.getStudentEmail().matches(emailRegex) || !studentDTO.getStudentBirthDate().matches(birthDateRegex)) {
            return -4; // 새로운 에러 코드
        }

        if (studentDTO.getStudentId() == null || studentDTO.getStudentPassword() == null || passwordCheck == null) {
            return -3; // 적절한 에러 코드 반환
        }

        // 아이디 중복 체크
        Optional<StudentEntity> existingStudent = studentRepository.findByStudentId(studentDTO.getStudentId());
        if (existingStudent.isPresent()) { // 아이디 중복
            return -1;
        } else if (!studentDTO.getStudentPassword().equals(passwordCheck)) { // 비밀번호 불일치
            return -2;
        } else { // 회원가입 성공
            StudentEntity studentEntity = StudentEntity.toStudent(studentDTO);
            studentEntity.setStudentBirthDate(studentDTO.getStudentBirthDate());
            studentEntity.setStudentPhoneNumber(studentDTO.getStudentPhoneNumber());
            studentRepository.save(studentEntity);
            return 1;
        }
    }

    // 로그인 로직
    public StudentDTO login(StudentDTO studentDTO) {
        Optional<StudentEntity> byStudentId = studentRepository.findByStudentId(studentDTO.getStudentId());

        if (byStudentId.isPresent()) {
            StudentEntity studentEntity = byStudentId.get();
            if (studentEntity.getStudentPassword().equals(studentDTO.getStudentPassword())) {
                System.out.println("로그인 성공!");

                return StudentDTO.toStudentDTO(studentEntity);
            } else {
                System.out.println("비밀번호가 일치하지 않습니다.");
                return null;
            }
        } else {
            System.out.println("존재하지 않는 아이디입니다.");
            return null;
        }
    }

    // 아이디 찾기
    public Optional<String> findStudentIdByStudentNameAndPhoneNumber(String studentName, String studentPhoneNumber) {
        return studentRepository.findStudentIdByStudentNameAndPhoneNumber(studentName, studentPhoneNumber);
    }

    // 비밀번호 찾기
    public Optional<StudentDTO> findStudentByStudentIdAndStudentNameAndPhoneNumber(String studentId, String studentName, String studentPhoneNumber) {
        return studentRepository.findByStudentIdAndStudentNameAndStudentPhoneNumber(studentId, studentName, studentPhoneNumber)
                .map(StudentDTO::toStudentDTO);
    }

    public Optional<StudentDTO> findStudentByEmail(String email) {
        return studentRepository.findByStudentEmail(email).map(StudentDTO::toStudentDTO);
    }

    public void updateStudentPassword(Long studentId, String newPassword) {
        studentRepository.findById(studentId).ifPresent(studentEntity -> {
            studentEntity.setStudentPassword(newPassword);
            studentRepository.save(studentEntity);
        });
    }

    //사용자 정보 수정
    public int updateStudent(StudentDTO studentDTO) {
        Optional<StudentEntity> existingStudent = studentRepository.findById(studentDTO.getId());
        if (existingStudent.isPresent()) {
            StudentEntity studentEntity = existingStudent.get();
            studentEntity.setStudentName(studentDTO.getStudentName());
            studentEntity.setStudentPhoneNumber(studentDTO.getStudentPhoneNumber());
            studentEntity.setStudentBirthDate(studentDTO.getStudentBirthDate());
            studentEntity.setProfilePictureUrl(studentDTO.getProfilePictureUrl());
            studentRepository.save(studentEntity);
            return 1; // 성공
        }
        return -1; // 실패
    }

    public StudentDTO findStudentById(Long id) {
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity.map(StudentDTO::toStudentDTO).orElse(null);
    }

    // 학생 찾기 (이름, 전화번호 이용)
    public StudentDTO findStudentByNameAndPhoneNumber(String name, String phonenumber) {
        Optional<StudentEntity> studentEntity = studentRepository.findByNameAndPhoneNumber(name, phonenumber);
        return (studentEntity.isPresent())? StudentDTO.toStudentDTO(studentEntity.get()) : null; // 학생 찾으면 DTO 리턴, 없으면 null
    }
}


