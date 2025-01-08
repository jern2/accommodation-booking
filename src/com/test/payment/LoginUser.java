package com.test.payment;

public class LoginUser {
    private int id;
    private String username;
    private String password;
    private String name;

    public LoginUser(int id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    
}