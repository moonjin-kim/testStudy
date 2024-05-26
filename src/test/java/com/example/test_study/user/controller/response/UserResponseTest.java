package com.example.test_study.user.controller.response;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    public void User으로_응답을_생성할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();
        //when
        UserResponse userResponse = UserResponse.from(user);

        //then
        assertThat(userResponse.getId()).isEqualTo(1);
        assertThat(userResponse.getEmail()).isEqualTo("test202@naver.com");
        assertThat(userResponse.getNickname()).isEqualTo("test202");
        assertThat(userResponse.getAddress()).isEqualTo("Ulsan");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}