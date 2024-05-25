package com.example.test_study.post.controller;
git
import com.example.test_study.post.controller.response.PostResponse;
import com.example.test_study.post.domain.PostUpdate;
import com.example.test_study.post.service.PostService;
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
            @RequestBody PostUpdate request
    ) {
        return ResponseEntity
                .ok()
                .body(PostResponse.toResponse(postService.update(request,id)));
    }

}
