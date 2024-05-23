package com.example.test_study.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
