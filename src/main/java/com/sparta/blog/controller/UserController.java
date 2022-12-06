package com.sparta.blog.controller;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.dto.UserResponseDto;
import com.sparta.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;

    //회원가입.
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    //로그인
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

}