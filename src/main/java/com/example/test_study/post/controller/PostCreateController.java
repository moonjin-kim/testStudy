package com.example.test_study.post.controller;

import com.example.test_study.user.domain.dto.PostCreateDto;
import com.example.test_study.user.domain.dto.PostResponse;
import com.example.test_study.user.service.PostService;
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
            @RequestBody PostCreateDto request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.toResponse(postService.create(request,userId)));
    }
}
