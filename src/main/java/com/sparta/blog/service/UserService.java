package com.sparta.blog.service;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.dto.UserResponseDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //회원가입부분.
    @Transactional
    public ResponseEntity<UserResponseDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        UserResponseDto userResponseDto;


        // 회원 중복 확인
        //Optional :  Optional<T> 클래스를 사용해 NPE를 방지할 수 있도록 도와준다. Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와준다.
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {

            userResponseDto = new UserResponseDto("중복된 사용자가 존재합니다.", 400);
            return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.BAD_REQUEST);
        } else {
            userResponseDto = new UserResponseDto("회원가입 성공", 200);
            User user = new User(username, password);
            userRepository.save(user);
        }
        return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.OK);
    }

    //로그인 부분.
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        UserResponseDto userResponseDto;

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //addHeader를 하면 Header에 값을 넣어줄 수 있다. Key값에는 AuTHORIZATION_HEADER와
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
        userResponseDto = new UserResponseDto("로그인 성공.", 200);
        return new ResponseEntity<UserResponseDto>(userResponseDto, HttpStatus.OK);
    }

}


