package com.example.test_study.post.service;

import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.post.infrastructure.PostEntity;
import com.example.test_study.post.infrastructure.PostRepository;
import com.example.test_study.user.infrastructure.UserEntity;
import com.example.test_study.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostEntity getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Transactional
    public PostEntity create(PostCreate request, Long userId) {
        UserEntity userEntity = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(request.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        postEntity.setWriter(userEntity);

        return postRepository.save(postEntity);
    }

    @Transactional
    public PostEntity update(PostUpdate request, long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        postEntity.setContent(request.getContent());
        return postRepository.save(postEntity);
    }
}
