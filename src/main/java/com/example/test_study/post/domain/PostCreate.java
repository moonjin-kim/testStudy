package com.example.test_study.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {
    private String content;

    @Builder
    public PostCreate(
            @JsonProperty("content") String content
    ) {
        this.content = content;
    }
}
