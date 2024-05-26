package com.example.test_study.user.controller.response;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MyProfileResponseTest {

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
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        //then
        assertThat(myProfileResponse.getId()).isEqualTo(1);
        assertThat(myProfileResponse.getEmail()).isEqualTo("test202@naver.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("test202");
        assertThat(myProfileResponse.getAddress()).isEqualTo("Ulsan");
        assertThat(myProfileResponse.getLastLogin()).isEqualTo(100L);
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}