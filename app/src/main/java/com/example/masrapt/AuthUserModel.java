package com.example.masrapt;

public class AuthUserModel {

    String email_or_username;
    String password;
    private int id;
    private String email;
    private String name;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AuthUserModel(String email_or_username, String password) {
        this.email_or_username = email_or_username;
        this.password = password;
    }

    public AuthUserModel(String username, String user_mail, String password) {
        this.name = username;
        this.email = user_mail;
        this.password = password;
    }

    public AuthUserModel(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public AuthUserModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
