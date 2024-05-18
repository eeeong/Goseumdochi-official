package com.midas.goseumdochi.academy.entity;

import com.midas.goseumdochi.student.entity.StudentEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "student_academy")
public class StudentAcademyEntity { // Student와 director 관계 엔터티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_academy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY: 지연로딩 (조회 시점을 실제 해당 객체가 사용될때로 늦출 수 있음)
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id")
    private AcademyEntity academyEntity;

    @Builder
    public StudentAcademyEntity(Long id, StudentEntity studentEntity, AcademyEntity academyEntity) {
        this.id = id;
        this.studentEntity = studentEntity;
        this.academyEntity = academyEntity;
    }
}
