package com.example.zerowastehubs.dto.auth;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LoginResponseDto {

    @SerializedName("token")
    private String token;

    @SerializedName("email")
    private String email;

    @SerializedName("roles")
    private List<String> roles;

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
