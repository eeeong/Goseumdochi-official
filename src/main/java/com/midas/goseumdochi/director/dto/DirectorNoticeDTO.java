package com.midas.goseumdochi.director.dto;

import java.sql.Date;

public class DirectorNoticeDTO {
    private String title;
    private String content;
    private String writer;
    private Date regdate;

    public DirectorNoticeDTO() {
    }

    public DirectorNoticeDTO(String title, String content, String writer, Date regdate) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.regdate = regdate;
    }

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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }
}