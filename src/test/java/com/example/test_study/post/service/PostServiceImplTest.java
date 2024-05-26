package com.example.test_study.post.service;

import com.example.test_study.mock.*;
import com.example.test_study.post.domain.Post;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceImplTest {

    @Autowired
    private PostServiceImpl postServiceImpl;
    @BeforeEach
    void init() {

        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.postServiceImpl = PostServiceImpl.builder()
                .clockHolder(new TestClockHolder(12341234L))
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .build();

        User user1 = User.builder()
                .id(1L)
                .email("koko202@naver.com")
                .nickname("test202")
                .address("Ulsan")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();

        fakeUserRepository.save(user1);

        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("'kok201@naver.com'")
                .nickname("test201")
                .address("Ulsan")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaaaaaaaa1a")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());

        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloWorld")
                .createdAt(12341234L)
                .updateAt(12341234L)
                .writer(user1)
                .build());

    }

    @Test
    void getById는_존재하는_게시물을_조회_가능하다(){
        //given
        //when
        Post result = postServiceImpl.getById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getContent()).isEqualTo("helloWorld");
        assertThat(result.getCreatedAt()).isEqualTo(12341234L);
        assertThat(result.getUpdateAt()).isEqualTo(12341234L);
    }

    @Test
    void postCreateDto로_게시글을_생성할_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .content("test")
                .build();

        //when
        Post result = postServiceImpl.create(postCreate,1L);
        //then
        Post response = postServiceImpl.getById(1L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getContent()).isEqualTo("helloWorld");
        assertThat(response.getCreatedAt()).isEqualTo(12341234L);
    }

    @Test
    void postUpdateDto로_게시글을_수정할_수_있다(){
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("test")
                .build();

        //when
        Post result = postServiceImpl.update(postUpdate, 1L);
        //then
        Post response = postServiceImpl.getById(1L);
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getContent()).isEqualTo("test");
        assertThat(response.getUpdateAt()).isEqualTo(12341234L);
    }

}