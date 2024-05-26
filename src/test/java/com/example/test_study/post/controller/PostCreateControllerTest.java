package com.example.test_study.post.controller;

import com.example.test_study.mock.TestContainer;
import com.example.test_study.post.controller.response.PostResponse;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .uuidHolder(()-> "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        PostCreate postCreate = PostCreate.builder()
                .content("helloworld")
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                    .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());
        // when
        ResponseEntity<PostResponse> result =  testContainer.postCreateController.create(1L,postCreate);
        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody().getCreateAt()).isEqualTo(2000L);
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("test202@naver.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("test202");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
