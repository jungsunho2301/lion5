package com.example.demo.global.exception;

public class AssignmentNotFoundException extends RuntimeException {
    public AssignmentNotFoundException(String message) {
        super(message);
    }
}