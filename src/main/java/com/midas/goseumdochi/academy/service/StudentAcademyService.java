package com.midas.goseumdochi.academy.service;

import com.midas.goseumdochi.academy.repository.AcademyRepository;
import com.midas.goseumdochi.academy.dto.StudentAcademyDTO;
import com.midas.goseumdochi.academy.entity.StudentAcademyEntity;
import com.midas.goseumdochi.academy.repository.StudentAcademyRepository;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAcademyService {
    private final StudentAcademyRepository studentAcademyRepository;
    private final StudentRepository studentRepository;
    private final AcademyRepository academyRepository;

    public void registStudentAcademy(StudentAcademyDTO studentAcademyDTO) {
        StudentAcademyEntity studentDirectorEntity = StudentAcademyEntity.builder()
                        .studentEntity(studentRepository.findById(studentAcademyDTO.getStudentId()).get())
                                .academyEntity(academyRepository.findById(studentAcademyDTO.getAcademyId()).get())
                                        .build();
        studentAcademyRepository.save(studentDirectorEntity);
    }
}
