package com.saniya.aijobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.saniya.aijobportal.dto.LoginRequest;
import com.saniya.aijobportal.dto.LoginResponse;
import com.saniya.aijobportal.dto.RegisterRequest;
import com.saniya.aijobportal.dto.RegisterResponse;
import com.saniya.aijobportal.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register API
    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    // Login API
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}