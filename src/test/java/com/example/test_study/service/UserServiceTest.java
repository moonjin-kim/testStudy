package com.example.test_study.service;

import com.example.test_study.exception.ResourceNotFoundException;
import com.example.test_study.repository.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        String email = "kok202@naver.com";

        //when
        UserEntity result = userService.getByEmail(email);

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
            UserEntity result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다(){
        //given
        Long id = 1L;

        //when
        UserEntity result = userService.getById(id);

        //then
        assertThat(result.getNickname()).isEqualTo("test202");
    }

    @Test
    void getById는_ACTIVE_상태가_아닌_유저는_찾을_수_없다(){
        //given
        Long id = 2L;

        //then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getById(id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}