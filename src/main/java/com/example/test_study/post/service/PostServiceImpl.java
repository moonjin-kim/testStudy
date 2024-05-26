package com.example.test_study.post.service;

import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.common.service.ClockHolder;
import com.example.test_study.post.controller.port.PostService;
import com.example.test_study.post.domain.Post;
import com.example.test_study.post.service.port.PostRepository;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Transactional
    public Post create(PostCreate requestCreate, Long userId) {
        User writer = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        Post post = Post.from(writer,requestCreate,clockHolder);
        return postRepository.save(post);
    }

    @Transactional
    public Post update(PostUpdate request, long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        post = post.update(request,clockHolder);
        return postRepository.save(post);
    }
}
