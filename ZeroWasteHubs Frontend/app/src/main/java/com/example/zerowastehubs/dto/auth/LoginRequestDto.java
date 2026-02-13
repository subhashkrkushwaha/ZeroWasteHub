package com.example.zerowastehubs.dto.auth;

import com.google.gson.annotations.SerializedName;

public class LoginRequestDto {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
