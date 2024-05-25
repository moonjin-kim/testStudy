package com.example.test_study.user.controller;

import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.controller.response.UserResponse;
import com.example.test_study.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserCreate request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.toResponse(userService.create(request)));
    }
}
