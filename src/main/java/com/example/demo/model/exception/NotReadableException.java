package com.example.demo.model.exception;

public class NotReadableException extends RuntimeException {
    public NotReadableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
