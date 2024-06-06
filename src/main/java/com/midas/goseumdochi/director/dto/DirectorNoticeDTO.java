package com.midas.goseumdochi.director.dto;

import java.sql.Date;

public class DirectorNoticeDTO {
    private Long num; // pk
    private String title;
    private String content;
    private String writer;
    private Date regdate;
    private Long directorId;

    public DirectorNoticeDTO() {
    }

    // directorId 만 없는 생성자
    public DirectorNoticeDTO(Long num, String title, String content, Date regdate) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
    }

    public Long getNum() {return num;}
    public void setNum(Long num) {this.num = num;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Long getDirectorId() {return directorId;}
    public void setDirectorId(Long directorId) {this.directorId = directorId;}
}