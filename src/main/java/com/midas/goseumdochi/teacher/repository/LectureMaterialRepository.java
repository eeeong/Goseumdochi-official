package com.midas.goseumdochi.teacher.repository;

import com.midas.goseumdochi.teacher.entity.LectureMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureMaterialRepository extends JpaRepository<LectureMaterialEntity, Long> {
}
