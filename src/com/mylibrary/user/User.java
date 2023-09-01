package com.mylibrary.user;

public class User {
    private final String userName;
    private final String userId;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "User Name: " + userName +
                ", User ID: " + userId;
    }

}