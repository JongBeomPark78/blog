package com.sparta.blog.dto;

import com.sparta.blog.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecordResponseDto {
    private String title;
    private String author;
    private String contents;
    private LocalDateTime createdAt;


    public RecordResponseDto(Record sample) {
        this.title = sample.getTitle();
        this.author = sample.getAuthor();
        this.contents = sample.getContents();
        this.createdAt = sample.getCreatedAt();
    }


}
