package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String msg;
    private long statusCode;

    public UserResponseDto(String msg, long statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
