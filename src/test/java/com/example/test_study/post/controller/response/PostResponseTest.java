package com.example.test_study.post.controller.response;

import com.example.test_study.post.domain.Post;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        //given
        Post post = Post.builder()
                .content("helloWorld")
                .writer(User.builder()
                        .email("test202@naver.com")
                        .nickname("test202")
                        .address("Ulsan")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                        .build())
                .createdAt(Clock.systemUTC().millis())
                .build();

        //when
        PostResponse postResponse = PostResponse.from(post);

        //then
        assertThat(postResponse.getContent()).isEqualTo("helloWorld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("test202");
        assertThat(postResponse.getWriter().getAddress()).isEqualTo("Ulsan");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}