package com.ziplink.exception.custom;

public class UserExistInDatabase extends RuntimeException{
    public UserExistInDatabase(String message) {
        super(message);
    }
}
