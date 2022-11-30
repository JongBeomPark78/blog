package com.sparta.blog.controller;

import com.sparta.blog.dto.RecordRequestDto;
import com.sparta.blog.dto.RecordResponseDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.entity.Record;
import com.sparta.blog.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/api/post")
    //createRecord = 게시글 작성 (제목, 작성자명, 비밀번호, 작성 내용을 저장. 저장된 게시글 Client로 반환하기.)
    //Post방식이기 때문에 body가 존재하고 body안에 우리가 원하는 저장 해야 되는 것들이 넘어온다.
    public Record createRecord(@RequestBody RecordRequestDto requestDto) {
        return recordService.createRecord(requestDto);
    }

    @GetMapping("/api/get")
    //전체 글 목록 조회하는 API
    public List<RecordResponseDto> getRecord(){
        return recordService.getRecord();
    }

    @GetMapping("/api/get/{id}")
    //특정 글 조회
    public RecordResponseDto getRecord(@PathVariable Long id) {
        return recordService.getRecords(id);
    }

    @PutMapping("/api/post/{id}")
    //게시글 수정
    public RecordResponseDto updateRecord(@PathVariable Long id, @RequestBody RecordRequestDto requestDto){
        return recordService.updateRecord(id, requestDto);
    }

    //게시글 삭제
    @DeleteMapping("/api/delete/{id}")
    public ResponseDto deleteRecord(@PathVariable Long id){
        return recordService.deleteRecord(id);
    }



}
