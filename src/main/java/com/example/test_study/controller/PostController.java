package com.example.test_study.controller;

import com.example.test_study.model.dto.*;
import com.example.test_study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getUserById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("id")  long id,
            @RequestBody PostUpdateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.update(request,id)));
    }

    @PostMapping("")
    public ResponseEntity<PostResponse> createUser(
            @RequestHeader("ID") Long userId,
            @RequestBody PostCreateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.create(request,userId)));
    }
}
