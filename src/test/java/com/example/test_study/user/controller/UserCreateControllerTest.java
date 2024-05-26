package com.example.test_study.user.controller;

import com.example.test_study.mock.TestContainer;
import com.example.test_study.user.controller.response.UserResponse;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserCreateControllerTest {
    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .build();
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .uuidHolder(()-> "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();


        //when
        ResponseEntity<UserResponse> result = testContainer.userCreateController
                .create(userCreate);

        //then

        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(result.getBody().getNickname()).isEqualTo("kok202");
        assertThat(testContainer.userRepository.getById(1L).getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");
        assertThat(testContainer.userRepository.getById(1L).getLastLoginAt()).isEqualTo(0L);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
    }
}
