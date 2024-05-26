package com.example.test_study.post.controller.port;

import com.example.test_study.post.domain.Post;
import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.domain.PostUpdate;

public interface PostService {
    public Post getById(long id);
    public Post create(PostCreate requestCreate, Long userId);
    public Post update(PostUpdate request, long id);
}
