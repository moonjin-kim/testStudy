package com.example.test_study.user.domain;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.mock.TestClockHolder;
import com.example.test_study.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Pangyo")
                .build();

        //when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa"));

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(user.getNickname()).isEqualTo("kok202");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

    }

    @Test
    public void User는_UserUpdate_객체로_수정할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kok202")
                .address("Seoul")
                .build();


        //when
        user = user.update(userUpdate);

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test202@naver.com");
        assertThat(user.getNickname()).isEqualTo("kok202");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

    }

    @Test
    public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();

        //when
        user = user.login(new TestClockHolder(100L));

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
    }
    @Test
    public void 유효한_인증_코드로_계정을_활성화_할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();

        //when
        user = user.certification("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

        //then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    @Test
    public void 잘못된_인증_코드로_계정을_활성화_하려면_에러가_발생한다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build();

        //when
        //then
        assertThatThrownBy(() -> {
            User result = user.certification("aaaaaaaa-aaaa-aaaa-aaaaaaaaaaaa");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


}