package com.example.test_study.user.service;

import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.dto.PostCreateDto;
import com.example.test_study.user.domain.dto.PostUpdateDto;
import com.example.test_study.post.repository.PostEntity;
import com.example.test_study.post.repository.PostRepository;
import com.example.test_study.user.repository.UserEntity;
import com.example.test_study.user.repository.UserRepository;
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
    public PostEntity create(PostCreateDto request, Long userId) {
        UserEntity userEntity = userRepository.findByIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(request.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        postEntity.setWriter(userEntity);

        return postRepository.save(postEntity);
    }

    @Transactional
    public PostEntity update(PostUpdateDto request, long id) {
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        postEntity.setContent(request.getContent());
        return postRepository.save(postEntity);
    }
}
