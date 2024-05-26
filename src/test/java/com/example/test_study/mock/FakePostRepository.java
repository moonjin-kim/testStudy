package com.example.test_study.mock;

import com.example.test_study.post.domain.Post;
import com.example.test_study.post.service.port.PostRepository;
import com.example.test_study.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Post> data = new ArrayList<>();
    @Override
    public Optional<Post> findById(long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Post save(Post post) {
        if(post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .createdAt(post.getCreatedAt())
                    .updateAt(post.getUpdateAt())
                    .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }
}