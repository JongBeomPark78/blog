package com.sparta.blog.dto;

import com.sparta.blog.entity.Record;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecordResponseDto {
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private long id;
    private String username;


    public RecordResponseDto(Record sample) {
        this.id = sample.getId();
        this.title = sample.getTitle();
        this.contents = sample.getContents();
        this.createdAt = sample.getCreatedAt();
        this.modifiedAt = sample.getModifiedAt();
        this.username = sample.getUsername();

    }


}
