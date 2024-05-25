package com.example.test_study.user.domain.dto;

import com.example.test_study.post.repository.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;

    private String content;

    private long createAt;

    private UserResponse writer;

    @Builder
    public PostResponse(Long id, String content, long createAt, UserResponse writer) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.writer = writer;
    }

    static public PostResponse toResponse(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getId())
                .writer(UserResponse.toResponse(postEntity.getWriter()))
                .content(postEntity.getContent())
                .createAt(postEntity.getCreatedAt())
                .build();
    }
}
