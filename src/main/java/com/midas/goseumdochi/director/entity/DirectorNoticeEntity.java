package com.midas.goseumdochi.director.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "notice_director")
@Getter @Setter
@ToString
@NoArgsConstructor
public class DirectorNoticeEntity {
    @Id
    @Column(name = "nNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(name = "nTitle", length = 25)
    private String title;

    @Column(name = "nContent", length = 1000)
    private String content;

    @Column(name = "nRegdate")
    private Date regdate;

    // 원장과 N:1 연결
    @ManyToOne
    @JoinColumn(name = "director_id")
    private DirectorEntity directorEntity;

    @Builder
    public DirectorNoticeEntity(Long num, String title, String content, Date regdate, DirectorEntity directorEntity) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.directorEntity = directorEntity;
    }
}