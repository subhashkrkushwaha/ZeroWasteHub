package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("userRoles")
    private List<UserRoleDto> userRoles;
    @SerializedName("createdAt")
    private String createdAt; // use String on Android
    public String getCreatedAt() { return createdAt; }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<UserRoleDto> getUserRoles() { return userRoles; }
}

