package com.example.test_study.post.controller;

import com.example.test_study.model.dto.*;
import com.example.test_study.user.domain.dto.PostResponse;
import com.example.test_study.user.domain.dto.PostUpdateDto;
import com.example.test_study.user.service.PostService;
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
    public ResponseEntity<PostResponse> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable("id")  long id,
            @RequestBody PostUpdateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.update(request,id)));
    }

}