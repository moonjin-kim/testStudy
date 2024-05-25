package com.example.test_study.service;

import com.example.test_study.exception.CertificationCodeNotMatchedException;
import com.example.test_study.exception.ResourceNotFoundException;
import com.example.test_study.model.UserStatus;
import com.example.test_study.model.dto.PostCreateDto;
import com.example.test_study.model.dto.PostUpdateDto;
import com.example.test_study.model.dto.UserCreateDto;
import com.example.test_study.model.dto.UserUpdateDto;
import com.example.test_study.repository.PostEntity;
import com.example.test_study.repository.UserEntity;
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
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_조회_가능하다(){
        //given
        //when
        PostEntity result = postService.getById(2L);

        //then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    void postCreateDto로_게시글을_생성할_수_있다(){
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .content("test")
                .build();

        //when
        PostEntity result = postService.create(postCreateDto,2L);
        //then
        PostEntity response = postService.getById(1L);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getContent()).isEqualTo("test");
        assertThat(response.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto로_게시글을_수정할_수_있다(){
        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("test")
                .build();

        //when
        PostEntity result = postService.update(postUpdateDto, 2L);
        //then
        PostEntity response = postService.getById(2L);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getContent()).isEqualTo("test");
        assertThat(response.getUpdateAt()).isGreaterThan(0);
    }

}