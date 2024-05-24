package com.example.test_study.repository;

import com.example.test_study.model.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("findByIdAndStatus로 유저 데이터를 찾아올 수 있다.")
    @Test
    void findByIdAndStatusTest(){
        //given

        //when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @DisplayName("findByIdAndStatus로 유저 데이터를 찾아올 수 있다.")
    @Test
    void findByIdAndStatusTestFail(){
        //given

        //when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(2L, UserStatus.ACTIVE);

        //then
        assertThat(result.isEmpty()).isTrue();

    }

    @DisplayName("findByEmailAndStatus는 데이터가 없으면 Optional empty를 내려준다")
    @Test
    void findByEmailAndStatusTest(){
        //given
        //when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("test@gmail.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isEmpty()).isTrue();

    }

    @DisplayName("findByEmailAndStatus는 데이터가 없으면 Optional empty를 내려준다")
    @Test
    void findByEmailAndStatusTestFail(){
        //given
        //when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("test@gmail.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isEmpty()).isTrue();

    }
}
