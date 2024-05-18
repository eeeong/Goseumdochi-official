package com.midas.goseumdochi.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "notice_admin")
@Getter @Setter
@ToString
@NoArgsConstructor
public class AdminNoticeEntity {
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
    public AdminNoticeEntity(int num, String title, String content, Date regdate, String writer) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.writer = writer;
    }
}
