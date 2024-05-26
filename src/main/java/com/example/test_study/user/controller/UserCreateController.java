package com.example.test_study.user.controller;

import com.example.test_study.user.controller.port.UserCreateService;
import com.example.test_study.user.controller.port.UserReadService;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.controller.response.UserResponse;
import lombok.Builder;
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
@Builder
public class UserCreateController {

    private final UserCreateService userCreateService;

    @PostMapping("")
    public ResponseEntity<UserResponse> create(
            @RequestBody UserCreate request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponse.from(userCreateService.create(request)));
    }
}
