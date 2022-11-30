package com.sparta.blog.service;

import com.sparta.blog.dto.RecordRequestDto;
import com.sparta.blog.dto.RecordResponseDto;
import com.sparta.blog.dto.ResponseDto;
import com.sparta.blog.entity.Record;
import com.sparta.blog.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService {
    //DB를 사용하기 위해서 레포지토리와 연결
    private final RecordRepository recordRepository;

    //글 생성하는 기능
    @Transactional
    public Record createRecord(RecordRequestDto requestDto) {
        //DB에 연결해서 저장을 하려면 @Entity 어노테이션이 붙어있는 Record 클래스를 인스턴스로 만들어서 그 값을 사용해서 저장해야한다.
        Record record = new Record(requestDto);
        recordRepository.save(record);
        return record;
    }

    //전체 조회하는 기능
    @Transactional(readOnly = true) //읽기 특화옵션
    public List<RecordResponseDto> getRecord() {
        List<Record> list = recordRepository.findAllByOrderByCreatedAtDesc();
        List<RecordResponseDto> Responsedto = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            Record sample = list.get(i);
            RecordResponseDto dto = new RecordResponseDto(sample);
            Responsedto.add(dto);
        }
        return Responsedto;
    }

    @Transactional(readOnly = true)
    public RecordResponseDto getRecords(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당자료 없다.")
        );
        return new RecordResponseDto(record);


    }

    @Transactional
    public RecordResponseDto updateRecord(Long id, RecordRequestDto requestDto) {
        //여기서 한번에 구현 Check
        Record record = recordRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당자료 없다.")
        );
        //비밀번호 check
        String check = record.getPassword();
        if(check.equals(requestDto.getPassword()) == true){
            record.update(requestDto);
            return new RecordResponseDto(record);
        }

        //record.update(requestDto);
        return new RecordResponseDto(record);
    }

    public ResponseDto deleteRecord(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당자료 없다.")
        );
        recordRepository.delete(record);
        return new ResponseDto("success:true");
    }
}
