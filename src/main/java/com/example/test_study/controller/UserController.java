package com.example.test_study.controller;

import com.example.test_study.model.dto.UserCreateDto;
import com.example.test_study.model.dto.UserResponse;
import com.example.test_study.model.dto.UserUpdateDto;
import com.example.test_study.service.UserService;
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
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id")  long id) {
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
    public ResponseEntity<Long> updateUser(
            @RequestHeader("ID") Long id,
            @RequestBody UserUpdateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(userService.update(id,request).getId());
    }

    @PostMapping("")
    public ResponseEntity<Long> createUser(
            @RequestBody UserCreateDto request
    ) {
        return ResponseEntity
                .ok()
                .body(userService.create(request).getId());
    }

}
