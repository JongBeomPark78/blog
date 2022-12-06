package com.sparta.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    @Column(nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Size(min = 8, max = 15)
    @Column(nullable = false)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}