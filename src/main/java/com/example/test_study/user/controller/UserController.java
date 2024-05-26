package com.example.test_study.user.controller;

import com.example.test_study.user.controller.port.AuthenticationService;
import com.example.test_study.user.controller.port.UserCreateService;
import com.example.test_study.user.controller.port.UserReadService;
import com.example.test_study.user.controller.port.UserUpdateService;
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
    private final UserCreateService userCreateService;
    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;
    private final AuthenticationService authenticationService;

    @ResponseStatus
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id")  long id) {
        return ResponseEntity
                .ok()
                .body(UserResponse.from(userReadService.getById(id)));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable("id") long id,
            @RequestParam("certificationCode") String certificationCode
    ) {
        authenticationService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<MyProfileResponse> getMyInfo(
            @RequestHeader("EMAIL") String email
    ) {
        User user = userReadService.getByEmail(email);
        authenticationService.login(user.getId());
        user = userReadService.getByEmail(email);
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
                .body(userUpdateService.update(id,request).getId());
    }

}
