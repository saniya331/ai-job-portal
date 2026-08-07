package com.saniya.aijobportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Email already exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    // Invalid login credentials
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentials(
            InvalidCredentialsException ex) {

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    // Any other application error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(
            RuntimeException ex) {

        ex.printStackTrace();

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    // Unexpected server error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            Exception ex) {

        ex.printStackTrace();

        return new ResponseEntity<>(
                "Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}