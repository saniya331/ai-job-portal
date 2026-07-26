package com.saniya.aijobportal.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saniya.aijobportal.dto.LoginRequest;
import com.saniya.aijobportal.dto.LoginResponse;
import com.saniya.aijobportal.dto.RegisterRequest;
import com.saniya.aijobportal.dto.RegisterResponse;
import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.exception.EmailAlreadyExistsException;
import com.saniya.aijobportal.exception.InvalidCredentialsException;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Register User
    public RegisterResponse registerUser(RegisterRequest request) {

        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Create User entity
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setCreatedAt(LocalDateTime.now());

        // Save user
        User savedUser = userRepository.save(user);

        // Prepare response
        RegisterResponse response = new RegisterResponse();
        response.setId(savedUser.getId());
        response.setFullName(savedUser.getFullName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setMessage("Registration Successful");

        return response;
    }

    // Login User
    public LoginResponse loginUser(LoginRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        // Check if email exists
        if (optionalUser.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = optionalUser.get();

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(user.getEmail());

        // Prepare response
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful");
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);

        return response;
    }
}