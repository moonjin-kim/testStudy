package com.example.test_study.medinum.service;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserUpdate;
import com.example.test_study.user.infrastructure.UserEntity;
import com.example.test_study.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("test")
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private JavaMailSender mailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        String email = "kok202@naver.com";

        //when
        User result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("test202");
    }

    @Test
    void getByEmail은_ACTIVE_상태가_아닌_유저는_찾을_수_없다(){
        //given
        String email = "kok201@naver.com";

        //when
        //then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        Long id = 2L;

        //when
        User result = userService.getById(id);

        //then
        assertThat(result.getNickname()).isEqualTo("test202");
    }

    @Test
    void getById는_ACTIVE_상태가_아닌_유저는_찾을_수_없다(){
        //given
        Long id = 3L;

        //then
        assertThatThrownBy(() -> {
            User result = userService.getById(id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto를_이용하여_유저를_생성할_수_있다(){
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("kok203@kakao.com")
                .address("Gyeongi")
                .nickname("kok202-k")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        //when
        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        // assertThat(result.getCertificationCode()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void UserUpdateDto를_이용하여_유저정보를_변경할_수_있다(){
        //given
        UserUpdate userCreateDto = UserUpdate.builder()
                .address("Gyeongi")
                .nickname("kok202-n")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        //when
        User result = userService.update(2,userCreateDto);

        //then
        User userEntity = userService.getById(2);
        assertThat(userEntity.getLastLoginAt()).isNotNull();
        assertThat(userEntity.getAddress()).isEqualTo("Gyeongi");
        assertThat(userEntity.getNickname()).isEqualTo("kok202-n");
        // assertThat(result.getCertificationCode()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void user을_로그인_시키면_마지막_로그인_시간이_변경된다(){
        //given
        //when
        userService.login(2);

        //then
        User user = userService.getById(2);
        assertThat(user.getLastLoginAt()).isGreaterThan(0);
        // assertThat(result.getCertificationCode()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다(){
        //given
        //when
        userService.verifyEmail(3,"aaaaaaaa-aaaa-aaaa-aaaaaaaaaa1a");

        //then
        User user = userService.getById(3);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        // assertThat(result.getCertificationCode()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void 잘못된_인증_코드로_인증_할_시_에러가_발생한다(){
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userService.verifyEmail(3,"aaaaaaaa-aaaa-aaaa-aaaaaaaaaaca");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}