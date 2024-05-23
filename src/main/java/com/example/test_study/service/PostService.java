package com.example.test_study.service;

import com.example.test_study.exception.ResourceNotFoundException;
import com.example.test_study.model.UserStatus;
import com.example.test_study.model.dto.PostCreateDto;
import com.example.test_study.model.dto.PostUpdateDto;
import com.example.test_study.model.dto.UserCreateDto;
import com.example.test_study.model.dto.UserUpdateDto;
import com.example.test_study.repository.PostEntity;
import com.example.test_study.repository.PostRepository;
import com.example.test_study.repository.UserEntity;
import com.example.test_study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;

    public PostEntity getByIdOrElseThrow(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
    }

    @Transactional
    public PostEntity createPost(PostCreateDto request, Long userId) {
        UserEntity userEntity = userRepository.findByIdAndStatus(userId,UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        PostEntity postEntity = new PostEntity();
        postEntity.setContent(request.getContent());
        postEntity.setCreateAt(LocalDateTime.now());
        postEntity.setUpdateAt(LocalDateTime.now());
        postEntity.setWriter(userEntity);

        return postRepository.save(postEntity);
    }

    @Transactional
    public PostEntity updatePost(long id, long userId, PostUpdateDto request) {
        UserEntity userEntity = userRepository.findByIdAndStatus(userId,UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        postEntity.setContent(request.getContent());
        return postRepository.save(postEntity);
    }
}
