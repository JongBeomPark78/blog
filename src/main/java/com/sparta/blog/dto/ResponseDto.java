package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String msg;


    public ResponseDto(String msg){
        this.msg = msg;
    }

}
