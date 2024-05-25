package com.example.test_study.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {
    private String content;

    @Builder
    public PostUpdateDto(
            @JsonProperty("content") String content
    ) {
        this.content = content;
    }
}
