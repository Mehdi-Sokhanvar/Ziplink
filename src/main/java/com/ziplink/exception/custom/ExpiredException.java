package com.ziplink.exception.custom;

public class ExpiredException extends RuntimeException {
    public ExpiredException(String message) {
        super(message);
    }
}
