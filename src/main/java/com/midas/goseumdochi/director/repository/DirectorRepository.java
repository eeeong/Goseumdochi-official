package com.midas.goseumdochi.director.repository;

import com.midas.goseumdochi.director.entity.DirectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorEntity, Long> {
    @Query("SELECT d FROM DirectorEntity d WHERE d.loginid = :loginid And d.password = :password")
    Optional<DirectorEntity> findByLoginidAndPassword(@Param("loginid") String loginid,
                                                                         @Param("password") String password);

    @Query("SELECT d FROM DirectorEntity d WHERE d.loginid = :loginid")
    Optional<DirectorEntity> findByLoginid(@Param("loginid") String loginid);

    // fk 조회
    @Query("SELECT d FROM DirectorEntity d WHERE d.academyEntity.id = :academyId")
    Optional<DirectorEntity> findByAcademyId(@Param("academyId") Long academyId);
}
