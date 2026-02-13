package com.example.zerowastehubs.dto.auth;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserRegisterDto {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("password")
    private String password;
    @SerializedName("createdAt")
    private String createdAt; // use String on Android
    public String getCreatedAt() { return createdAt; }
    @SerializedName("roleIds")
    private List<Integer> roleIds;

    public UserRegisterDto(String name, String email, String phoneNumber, String password, List<Integer> roleIds,String createdAt) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roleIds = roleIds;
        this.createdAt=createdAt;
    }
    public UserRegisterDto(String name, String email, String phoneNumber, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
    }
}
