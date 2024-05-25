package com.example.test_study.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateDto {
    private String content;

    @Builder
    public PostCreateDto(
            @JsonProperty("content") String content
    ) {
        this.content = content;
    }
}
