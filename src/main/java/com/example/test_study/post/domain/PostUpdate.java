package com.example.test_study.post.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdate {
    private String content;

    @Builder
    public PostUpdate(
            @JsonProperty("content") String content
    ) {
        this.content = content;
    }
}
