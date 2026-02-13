package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;

public class UserRoleDto {

    @SerializedName("id")
    private Integer id;
    @SerializedName("userRole")
    private String userRole;

    @SerializedName("createdAt")
    private String createdAt; // use String on Android

    @SerializedName("updatedAt")
    private String updatedAt; // use String on Android

    public Integer getId() { return id; }
    public String getUserRole() { return userRole; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }
}


