package com.midas.goseumdochi.admin.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "admin")
@Getter
@ToString
@NoArgsConstructor
public class AdminEntity {
    @Id
    @Column(name = "aNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;

    @Column(name = "aId")
    private String id;

    @Column(name = "aName")
    private String name;

    @Column(name = "aPassword")
    private String password;

    @Builder
    public AdminEntity(int num, String id, String name, String password) {
        this.num = num;
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
