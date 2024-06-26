package com.example.test_study.user.controller;

import com.example.test_study.user.controller.port.UserService;
import com.example.test_study.user.controller.response.MyProfileResponse;
import com.example.test_study.user.controller.response.UserResponse;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
@Builder
public class UserController {
    private final UserService userService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(UserResponse.from(userService.getById(id)));
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
    public ResponseEntity<MyProfileResponse> getMyInfo(
            @RequestHeader("EMAIL") String email
    ) {
        User user = userService.getByEmail(email);
        userService.login(user.getId());
        user = userService.getByEmail(email);
        return ResponseEntity
                .ok()
                .body(MyProfileResponse.from(user));
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
