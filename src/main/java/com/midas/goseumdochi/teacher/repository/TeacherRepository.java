package com.midas.goseumdochi.teacher.repository;

import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    //fk로 찾기
    @Query("SELECT t FROM TeacherEntity t WHERE t.academyEntity.id = :academyId")
    List<TeacherEntity> findAllByAcademyId(@Param("academyId") Long academyId);

    // 로그인 ID로 선생님을 찾는 쿼리 메서드
    TeacherEntity findByLoginid(String loginid);
}
