package com.example.test_study.service;

import com.example.test_study.exception.ResourceNotFoundException;
import com.example.test_study.model.UserStatus;
import com.example.test_study.model.dto.PostCreateDto;
import com.example.test_study.model.dto.PostUpdateDto;
import com.example.test_study.repository.PostEntity;
import com.example.test_study.repository.PostRepository;
import com.example.test_study.repository.UserEntity;
import com.example.test_study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

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
        UserEntity userEntity = userRepository.findByIdAndStatus(userId,UserStatus.ACTIVE)
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
