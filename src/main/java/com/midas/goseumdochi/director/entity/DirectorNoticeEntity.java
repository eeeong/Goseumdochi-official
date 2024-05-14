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
    private int num;

    @Column(name = "nTitle", length = 25)
    private String title;

    @Column(name = "nContent", length = 1000)
    private String content;

    @Column(name = "nRegdate")
    private Date regdate;

    @Column(name = "nWriter")
    private String writer;

    @Builder
    public DirectorNoticeEntity(int num, String title, String content, Date regdate, String writer) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.writer = writer;
    }
}
