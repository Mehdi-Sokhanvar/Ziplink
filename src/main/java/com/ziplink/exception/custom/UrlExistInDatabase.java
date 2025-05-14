package com.ziplink.exception.custom;

public class UrlExistInDatabase extends RuntimeException {
    public UrlExistInDatabase(String message) {
        super(message);
    }
}
