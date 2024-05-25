package com.example.test_study.user.controller;

import com.example.test_study.user.controller.response.UserResponse;
import com.example.test_study.user.domain.UserUpdate;
import com.example.test_study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(UserResponse.toResponse(userService.getById(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable("id") long id,
            @RequestParam("certificationCode") String certificationCode
    ) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(
            @RequestHeader("EMAIL") String email
    ) {
        return ResponseEntity
                .ok()
                .body(UserResponse.toResponse(userService.getByEmail(email)));
    }


    @PutMapping("/me")
    public ResponseEntity<Long> update(
            @RequestHeader("ID") Long id,
            @RequestBody UserUpdate request
    ) {
        return ResponseEntity
                .ok()
                .body(userService.update(id,request).getId());
    }

}
