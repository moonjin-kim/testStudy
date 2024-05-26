package com.example.test_study.user.service;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.mock.FakeMailSender;
import com.example.test_study.mock.FakeUserRepository;
import com.example.test_study.mock.TestClockHolder;
import com.example.test_study.mock.TestUuidHolder;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class UserServiceTest {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userServiceImpl = UserServiceImpl.builder()
                .certificationService(new CertificationService(fakeMailSender))
                .clockHolder(new TestClockHolder(12341234))
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa"))
                .userRepository(fakeUserRepository).build();

        fakeUserRepository.save(User.builder()
                        .id(1L)
                        .email("koko202@naver.com")
                        .nickname("test202")
                        .address("Ulsan")
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                        .status(UserStatus.ACTIVE)
                        .lastLoginAt(0L)
                .build());

        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("'kok201@naver.com'")
                .nickname("test201")
                .address("Ulsan")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaaaa1a")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());

    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        String email = "koko202@naver.com";

        //when
        User result = userServiceImpl.getByEmail(email);

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
            User result = userServiceImpl.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        Long id = 1L;

        //when
        User result = userServiceImpl.getById(id);

        //then
        assertThat(result.getNickname()).isEqualTo("test202");
    }

    @Test
    void getById는_ACTIVE_상태가_아닌_유저는_찾을_수_없다(){
        //given
        Long id = 2L;

        //then
        assertThatThrownBy(() -> {
            User result = userServiceImpl.getById(id);
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

        //when
        User result = userServiceImpl.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");
    }

    @Test
    void UserUpdateDto를_이용하여_유저정보를_변경할_수_있다(){
        //given
        UserUpdate userCreateDto = UserUpdate.builder()
                .address("Gyeongi")
                .nickname("kok202-n")
                .build();

        //when
        User result = userServiceImpl.update(1,userCreateDto);

        //then
        User userEntity = userServiceImpl.getById(1);
        assertThat(userEntity.getLastLoginAt()).isNotNull();
        assertThat(userEntity.getAddress()).isEqualTo("Gyeongi");
        assertThat(userEntity.getNickname()).isEqualTo("kok202-n");
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");
    }

    @Test
    void user을_로그인_시키면_마지막_로그인_시간이_변경된다(){
        //given
        //when
        userServiceImpl.login(1);

        //then
        User user = userServiceImpl.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(12341234);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다(){
        //given
        //when
        userServiceImpl.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaaaaaaaa1a");

        //then
        User user = userServiceImpl.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 잘못된_인증_코드로_인증_할_시_에러가_발생한다(){
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            userServiceImpl.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaaaaaaaaca");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}