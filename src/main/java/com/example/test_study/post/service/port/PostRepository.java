package com.example.test_study.post.service.port;

import com.example.test_study.post.domain.Post;

import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(long id);

    Post save(Post post);
}
