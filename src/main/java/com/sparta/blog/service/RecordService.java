package com.sparta.blog.service;

import com.sparta.blog.dto.RecordRequestDto;
import com.sparta.blog.dto.RecordResponseDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.entity.Record;
import com.sparta.blog.entity.User;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.RecordRepository;
import com.sparta.blog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {
    //DB를 사용하기 위해서 레포지토리와 연결
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    //글 생성하는 기능
    @Transactional
    public RecordResponseDto createRecord(RecordRequestDto requestDto,  HttpServletRequest request) {
        //DB에 연결해서 저장을 하려면 @Entity 어노테이션이 붙어있는 Record 클래스를 인스턴스로 만들어서 그 값을 사용해서 저장해야한다.
        //Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        //토큰이 있는 경우에만 글 등록 가능
        if(token != null){
            //Token 검증
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Record record = recordRepository.save(new Record(requestDto, user.getId(), user.getUsername()));


            return new RecordResponseDto(record);
        } else {
            return null;
        }
    }

    //전체 조회하는 기능
    @Transactional(readOnly = true) //읽기 특화옵션
    public List<RecordResponseDto> getRecord(HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if(token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
        }

        List<Record> list = recordRepository.findAllByOrderByCreatedAtDesc();
        List<RecordResponseDto> Responsedto = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Record sample = list.get(i);
            RecordResponseDto dto = new RecordResponseDto(sample);
            Responsedto.add(dto);
        }
        return Responsedto;
    }

    //특정 글 가져오기
    @Transactional(readOnly = true)
    public RecordResponseDto getRecords(long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if(token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            }
        Record record = recordRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 자료 없다.")
        );
        return new RecordResponseDto(record);

    }

    //수정하기
    @Transactional
    public RecordResponseDto updateRecord(Long id, RecordRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if(token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Record record = recordRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당자료 없다.")
            );

            record.update(requestDto,id,user.getUsername());
            return new RecordResponseDto(record);
        }else {
            return null;
        }

    }

    //삭제하기.
    public ResponseDto deleteRecord(Long id, HttpServletRequest request) {
        //Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        //토큰이 있는 경우에만 글 등록 가능
        if(token != null) {
            //Token 검증
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

        }
        Record record = recordRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당자료 없다.")
        );
        recordRepository.delete(record);
        return new ResponseDto("success:true");
    }
}
