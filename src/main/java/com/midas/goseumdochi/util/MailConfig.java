package com.midas.goseumdochi.util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


// yml에 적용이 안되서 코드로 직접 작성해서 설정함..
@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com"); // SMTP 서버 주소
        mailSender.setPort(587); // SMTP 서버 포트
        mailSender.setUsername("goseum24@naver.com"); // 메일 서버 사용자 이름
        mailSender.setPassword("goseumdochi4"); // 메일 서버 비밀번호

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
