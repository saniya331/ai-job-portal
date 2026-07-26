package com.saniya.aijobportal.dto;

public class LoginResponse {

    private String message;
    private String fullName;
    private String email;
    private String role;
    private String token;   // NEW FIELD

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // NEW Getter
    public String getToken() {
        return token;
    }

    // NEW Setter
    public void setToken(String token) {
        this.token = token;
    }
}