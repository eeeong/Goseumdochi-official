package com.midas.goseumdochi.academy.repository;

import com.midas.goseumdochi.academy.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    @Query("SELECT s FROM SubjectEntity s WHERE s.name = :name And s.academyEntity.id = :academyId")
    Optional<SubjectEntity> findByNameAndAcademyId(@Param("name") String name, @Param("academyId") Long academyId);
}
