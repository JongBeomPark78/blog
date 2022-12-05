package com.sparta.blog.entity;

import com.sparta.blog.dto.RecordRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Record extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String contents;

    //User와 연결을 위해서 생성했다.
    @Column(nullable = false)
    private Long userId;

    public Record(RecordRequestDto requestDto, Long userId){
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
        this.userId = requestDto.getuserId();
    }


    public void update(RecordRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContents();
    }
}
