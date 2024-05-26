package com.example.test_study.post.controller;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.mock.TestContainer;
import com.example.test_study.post.controller.response.PostResponse;
import com.example.test_study.post.domain.Post;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .uuidHolder(()-> "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        User user = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .writer(user)
                .content("helloWorld")
                .createdAt(200L)
                .updateAt(200L)
                .build());
        // when
        ResponseEntity<PostResponse> result =  testContainer.postController.getById(1L);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getCreateAt()).isEqualTo(200L);
        assertThat(result.getBody().getContent()).isEqualTo("helloWorld");
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("test202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .uuidHolder(()-> "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        // when
        // then
        assertThatThrownBy(() ->
                testContainer.postController
                        .getById(12341234L)

        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .uuidHolder(()-> "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        User user = testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .writer(user)
                .content("helloWorld")
                .createdAt(200L)
                .updateAt(200L)
                .build());
        // when
        ResponseEntity<PostResponse> result =  testContainer.postController.update(1L, postUpdate);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody().getCreateAt()).isEqualTo(200L);
        assertThat(result.getBody().getUpdateAt()).isEqualTo(2000L);
        assertThat(result.getBody().getContent()).isEqualTo("foobar");
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("test202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);

        // when
    }
}
