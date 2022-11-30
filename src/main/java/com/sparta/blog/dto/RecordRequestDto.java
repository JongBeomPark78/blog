package com.sparta.blog.dto;

import com.sparta.blog.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecordRequestDto {
    private String title;
    private String author;
    private String password;
    private String contents;

    public RecordRequestDto(Record record) {
        this.title = record.getTitle();
        this.author = record.getAuthor();
        this.contents = record.getContents();
        this.password = record.getPassword();
    }
}


