package com.example.test_study.post.controller;

import com.example.test_study.post.domain.PostCreate;
import com.example.test_study.post.controller.response.PostResponse;
import com.example.test_study.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/posts")
@RequiredArgsConstructor
public class PostCreateController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<PostResponse> create(
            @RequestHeader("ID") Long userId,
            @RequestBody PostCreate request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(request,userId)));
    }
}
