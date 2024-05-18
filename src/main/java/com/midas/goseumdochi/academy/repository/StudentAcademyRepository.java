package com.midas.goseumdochi.academy.repository;

import com.midas.goseumdochi.academy.entity.StudentAcademyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAcademyRepository extends JpaRepository<StudentAcademyEntity, Long> {
}
