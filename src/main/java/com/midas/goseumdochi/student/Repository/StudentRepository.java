package com.midas.goseumdochi.student.Repository;

import com.midas.goseumdochi.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByStudentId(String studentId);

    // 아이디 찾기를 위한 쿼리, studentPhoneNumber 필드 사용
    @Query("SELECT s.studentId FROM StudentEntity s WHERE s.studentName = :studentName AND s.studentPhoneNumber = :studentPhoneNumber")
    Optional<String> findStudentIdByStudentNameAndPhoneNumber(@Param("studentName") String studentName, @Param("studentPhoneNumber") String studentPhoneNumber);

    // 비밀번호 찾기를 위한 쿼리, studentPhoneNumber 필드 사용
    @Query("SELECT s FROM StudentEntity s WHERE s.studentId = :studentId AND s.studentName = :studentName AND s.studentPhoneNumber = :studentPhoneNumber")
    Optional<StudentEntity> findByStudentIdAndStudentNameAndStudentPhoneNumber(String studentId, String studentName, String studentPhoneNumber);
    Optional<StudentEntity> findByStudentEmail(String email);

    @Query("SELECT s FROM StudentEntity s WHERE s.studentName = :studentName And s.studentPhoneNumber = :studentPhoneNumber")
    Optional<StudentEntity> findByNameAndPhoneNumber(@Param("studentName") String studentName, @Param("studentPhoneNumber") String studentPhoneNumber);
}
