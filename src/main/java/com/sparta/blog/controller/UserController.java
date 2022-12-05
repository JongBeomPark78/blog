package com.sparta.blog.controller;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;

    /*@GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }*/

    //회원가입.
    @PostMapping("/signup")
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return userService.signup(signupRequestDto);
    }

    //로그인
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }

}