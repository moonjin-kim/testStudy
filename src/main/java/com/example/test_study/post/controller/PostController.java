package com.example.test_study.post.controller;

import com.example.test_study.post.controller.port.PostService;
import com.example.test_study.post.controller.response.PostResponse;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.post.service.PostServiceImpl;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/posts")
@RequiredArgsConstructor
@Builder
public class PostController {
    private final PostService postService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable("id")  long id,
            @RequestBody PostUpdate request
    ) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.update(request,id)));
    }

}
