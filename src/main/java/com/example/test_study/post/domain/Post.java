package com.example.test_study.post.domain;

import com.example.test_study.common.service.ClockHolder;
import com.example.test_study.post.infrastructure.PostEntity;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.infrastructure.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.Clock;

@Getter
public class Post {
    private final Long id;
    private final String content;
    private final Long createdAt;
    private final Long updateAt;
    private final User writer;

    @Builder
    public Post(Long id, String content, Long createdAt, Long updateAt, User writer) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.writer = writer;
    }

    public static Post from(User writer, PostCreate postCreate, ClockHolder clockHolder) {
        return Post.builder()
                .content(postCreate.getContent())
                .writer(writer)
                .createdAt(clockHolder.millis())
                .build();
    }

    public Post update(PostUpdate postUpdate, ClockHolder clockHolder) {
        return Post.builder()
                .id(id)
                .content(postUpdate.getContent())
                .writer(writer)
                .updateAt(clockHolder.millis())
                .createdAt(createdAt)
                .build();
    }
}
