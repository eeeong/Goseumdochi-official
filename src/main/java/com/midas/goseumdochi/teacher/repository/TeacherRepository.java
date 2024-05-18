package com.midas.goseumdochi.teacher.repository;

import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    //fk로 찾기
    @Query("SELECT t FROM TeacherEntity t WHERE t.academyEntity.id = :academyId")
    List<TeacherEntity> findAllByAcademyId(@Param("academyId") Long academyId);

    @Query("SELECT t FROM TeacherEntity t WHERE t.loginid = :loginid")
    Optional<TeacherEntity> findByLoginid(@Param("loginid") String loginid);
}
