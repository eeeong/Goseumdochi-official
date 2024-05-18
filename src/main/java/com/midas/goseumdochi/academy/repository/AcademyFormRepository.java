package com.midas.goseumdochi.academy.repository;

import com.midas.goseumdochi.academy.entity.AcademyFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademyFormRepository extends JpaRepository<AcademyFormEntity, Long> {
    @Query("SELECT af FROM AcademyFormEntity af WHERE af.directorName = :directorName And af.directorPhoneNumber = :directorPhoneNumber")
    Optional<AcademyFormEntity> findByDirectorNameAndDirectorPhoneNumber(@Param("directorName") String directorName,
                                                                         @Param("directorPhoneNumber") String directorPhoneNumber);
}
