package com.sparta.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String msg;
    private HttpStatus httpStatus;

    public ResponseDto(String msg){
        this.msg = msg;
    }

    public ResponseDto(String msg, HttpStatus httpStatus){
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

}
