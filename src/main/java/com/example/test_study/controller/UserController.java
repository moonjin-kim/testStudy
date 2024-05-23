package com.example.test_study.controller;

import com.example.test_study.repository.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v0/user")
@RequiredArgsConstructor
public class UserController {
}
