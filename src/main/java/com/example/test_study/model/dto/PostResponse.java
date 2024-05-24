package com.example.test_study.model.dto;

import com.example.test_study.repository.PostEntity;
import lombok.Builder;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;

    private String content;

    private long createAt;

    private long updateAt;

    private String writer;

    @Builder
    public PostResponse(Long id, String content, long createAt, long updateAt, String writer) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.writer = writer;
    }

    static public PostResponse toResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getId())
                .writer(postEntity.getWriter().getNickname())
                .content(postEntity.getContent())
                .createAt(postEntity.getCreatedAt())
                .updateAt(postEntity.getUpdateAt())
                .build();
    }
}
