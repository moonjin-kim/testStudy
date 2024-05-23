package com.example.test_study.controller;

import com.example.test_study.model.dto.*;
import com.example.test_study.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v0/posts")
@RequiredArgsConstructor
public class PostController {
    PostService postService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getUserById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.getByIdOrElseThrow(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePost(
            @RequestHeader("ID") Long userId,
            @PathVariable("id")  long id,
            @RequestBody PostUpdateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(postService.updatePost(id,userId,request).getId());
    }

    @PostMapping("")
    public ResponseEntity<Long> createUser(
            @RequestHeader("ID") Long userId,
            @RequestBody PostCreateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(postService.createPost(request,userId).getId());
    }
}
