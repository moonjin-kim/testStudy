package com.example.test_study.post.domain;

import com.example.test_study.mock.TestClockHolder;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    void PostCreate으로_으로_게시물을_만들_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .content("helloWorld")
                .build();

        User writer = User.builder()
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();

        //when
        Post post = Post.from(writer,postCreate,new TestClockHolder(100L));

        //then
        assertThat(post.getContent()).isEqualTo("helloWorld");
        assertThat(post.getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("test202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Ulsan");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

    }

    @Test
    void PostUpdate으로_으로_게시물을_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("helloWorld")
                .build();

        User writer = User.builder()
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("helloWorld")
                .createdAt(123123123L)
                .updateAt(0L)
                .writer(writer)
                .build();

        //when
        post = post.update(postUpdate,new TestClockHolder(123123123L));

        //then
        assertThat(post.getContent()).isEqualTo("helloWorld");
        assertThat(post.getCreatedAt()).isEqualTo(123123123L);
        assertThat(post.getUpdateAt()).isEqualTo(123123123L);
        assertThat(post.getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("test202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Ulsan");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

    }
}
