package com.finances.controller;

import com.finances.dto.request.LoginRequest;
import com.finances.dto.request.RegisterRequest;
import com.finances.dto.response.ApiResponse;
import com.finances.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/user/login")
    public ResponseEntity<ApiResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(new ApiResponse(userService.login(loginRequest), true));
    }

    @PostMapping("/public/user/register")
    public ResponseEntity<ApiResponse> register(RegisterRequest registerRequest) {
        return ResponseEntity.ok(new ApiResponse(userService.register(registerRequest), true));
    }
}
