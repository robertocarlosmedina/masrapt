package com.example.masrapt;

public class AuthUserModel {

    String name_or_email;
    String password;

    public AuthUserModel(String name_or_email, String password){
        this.name_or_email = name_or_email;
        this.password = password;
    }
}
