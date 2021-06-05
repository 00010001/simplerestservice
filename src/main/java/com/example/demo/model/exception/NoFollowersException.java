package com.example.demo.model.exception;

public class NoFollowersException extends RuntimeException {
    public NoFollowersException(String errorMessage) {
        super(errorMessage);
    }
}
