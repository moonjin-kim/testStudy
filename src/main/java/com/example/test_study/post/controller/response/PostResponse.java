package com.example.test_study.post.controller.response;

import com.example.test_study.post.infrastructure.PostEntity;
import com.example.test_study.user.controller.response.UserResponse;
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
