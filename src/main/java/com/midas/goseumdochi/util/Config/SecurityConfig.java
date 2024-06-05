package com.midas.goseumdochi.util.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/integrate/login").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/director/**").permitAll()
                        .requestMatchers("/api/academy/form/**").permitAll()
                        .requestMatchers("/api/teacher/**").permitAll()
                        .requestMatchers("/api/student/signup").permitAll()
                        .requestMatchers("/api/student/login").permitAll()
                        .requestMatchers("/api/student/logout").permitAll()
                        .requestMatchers("/api/student/findStudentId").permitAll()
                        .requestMatchers("/api/student/findStudentPassword").permitAll()
                        .requestMatchers("/api/student/info").permitAll()
                        .requestMatchers("/api/student/update").permitAll()
                        .requestMatchers("/api/student/uploadProfilePicture").permitAll()
                        .requestMatchers("/api/student/changePassword").permitAll()
                        .requestMatchers("/api/academy/form").permitAll()  // 회원가입 엔드포인트 인증 비활성화
                        .requestMatchers("/api/posts/upload").permitAll()  // 커뮤니티 게시글 작성
                        .requestMatchers("/api/posts/list").permitAll()
                        .requestMatchers("/api/admin/management/students").permitAll()
                        .requestMatchers("/api/admin/management/academy").permitAll()
                        .requestMatchers("/api/posts/${id}/with-comments").permitAll()
                        .requestMatchers("/api/mypage/posts/{id}").permitAll()
                        .requestMatchers("/api/mypage/liked-posts/**").permitAll()
                        .requestMatchers("/api/mypage/commented-posts/**").permitAll()
                        .requestMatchers("/api/posts/category/**").permitAll()
                        .requestMatchers("/api/posts/categories").permitAll()
                        .requestMatchers("/api/posts").permitAll()
                        .requestMatchers("/api/academy-reviews").permitAll()
                        .requestMatchers("/api/academies").permitAll()
                        .requestMatchers("/api/admin/main/info").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}