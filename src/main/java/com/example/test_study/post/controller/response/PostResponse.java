package com.example.test_study.post.controller.response;

import com.example.test_study.post.domain.Post;
import com.example.test_study.user.controller.response.UserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;

    private String content;

    private long createAt;

    private long updateAt;

    private UserResponse writer;

    @Builder
    public PostResponse(Long id, String content, long createAt,long updateAt, UserResponse writer) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.writer = writer;
    }

    static public PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .writer(UserResponse.from(post.getWriter()))
                .content(post.getContent())
                .createAt(post.getCreatedAt())
                .updateAt(post.getUpdateAt())
                .build();
    }
}
