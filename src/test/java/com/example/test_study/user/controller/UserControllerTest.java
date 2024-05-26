package com.example.test_study.user.controller;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.common.service.ClockHolder;
import com.example.test_study.mock.TestContainer;
import com.example.test_study.user.controller.port.UserReadService;
import com.example.test_study.user.controller.response.MyProfileResponse;
import com.example.test_study.user.controller.response.UserResponse;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserControllerTest {
    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                        .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());


        //when
        ResponseEntity<UserResponse> result = testContainer.userController
                .getById(1L);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getEmail()).isEqualTo("test202@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("test202");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 존재하지_않는_아이디로_유저를_호출하면_404에러가_발생한다(){
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();


        //when
        //then
        assertThatThrownBy(() ->
                testContainer.userController
                        .getById(12341234)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());


        //when
        ResponseEntity<Void> result = testContainer.userController
                .verifyEmail(1L,"aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(302));
        assertThat(testContainer.userRepository.getById(1).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());


        //when
        assertThatThrownBy(() ->
                testContainer.userController
                        .verifyEmail(1L,"aaaaaaaa-aaaa-aaaa-aaaaaaaa2aaa")
        ).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 2000L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("test202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .build());


        ResponseEntity<MyProfileResponse> result = testContainer.userController
                .getMyInfo("test202@naver.com");

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getEmail()).isEqualTo("test202@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("test202");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getLastLogin()).isEqualTo(2000L);
        assertThat(result.getBody().getAddress()).isEqualTo("Ulsan");
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
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


        ResponseEntity<Long> result = testContainer.userController
                .update(1L, UserUpdate.builder()
                        .address("Seoul")
                        .nickname("test203")
                        .build());

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualTo(1L);
        assertThat(testContainer.userRepository.getById(1L).getAddress()).isEqualTo("Seoul");
        assertThat(testContainer.userRepository.getById(1L).getLastLoginAt()).isEqualTo(100L);
        assertThat(testContainer.userRepository.getById(1L).getNickname()).isEqualTo("test203");
    }
}
