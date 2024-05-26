package com.example.test_study.medinum.service;

import com.example.test_study.post.domain.Post;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.post.infrastructure.PostEntity;
import com.example.test_study.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Post result = postService.getById(2L);

        //then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    void postCreateDto로_게시글을_생성할_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .content("test")
                .build();

        //when
        Post result = postService.create(postCreate,2L);
        //then
        Post response = postService.getById(1L);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getContent()).isEqualTo("test");
        assertThat(response.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto로_게시글을_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("test")
                .build();

        //when
        Post result = postService.update(postUpdate, 2L);
        //then
        Post response = postService.getById(2L);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getContent()).isEqualTo("test");
        assertThat(response.getUpdateAt()).isGreaterThan(0);
    }

}