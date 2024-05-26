package com.example.test_study.mock;

import com.example.test_study.common.service.ClockHolder;
import com.example.test_study.common.service.UuidHolder;
import com.example.test_study.post.controller.PostController;
import com.example.test_study.post.controller.PostCreateController;
import com.example.test_study.post.controller.PostCreateControllerTest;
import com.example.test_study.post.controller.port.PostService;
import com.example.test_study.post.service.PostServiceImpl;
import com.example.test_study.post.service.port.PostRepository;
import com.example.test_study.user.controller.UserController;
import com.example.test_study.user.controller.UserCreateController;
import com.example.test_study.user.controller.port.*;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.service.CertificationService;
import com.example.test_study.user.service.UserServiceImpl;
import com.example.test_study.user.service.port.MailSender;
import com.example.test_study.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostCreateController postCreateController;
    public final PostController postController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .clockHolder(clockHolder)
                .uuidHolder(uuidHolder)
                .certificationService(this.certificationService)
                .userRepository(this.userRepository)
                .build();
        this.userController = UserController.builder()
                .userService(userService)
                .build();

        this.userCreateController = UserCreateController.builder()
                .userService(userService)
                .build();

        this.postCreateController = PostCreateController.builder()
                .postService(postService)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .build();
    }
}
