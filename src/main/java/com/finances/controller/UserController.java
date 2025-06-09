package com.finances.controller;

import com.finances.dto.request.LoginRequest;
import com.finances.dto.request.RegisterRequest;
import com.finances.dto.response.ApiResponse;
import com.finances.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/public/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(
                new ApiResponse(
                        userService.login(loginRequest), true
                )
        );
    }

    @PostMapping("/public/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(
                new ApiResponse(
                        userService.register(registerRequest), true
                )
        );
    }
}
