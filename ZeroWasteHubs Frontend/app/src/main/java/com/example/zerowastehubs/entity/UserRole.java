package com.example.zerowastehubs.entity;

import com.google.gson.annotations.SerializedName;

public class UserRole {

    @SerializedName("id")
    private Integer id;

    @SerializedName("userRole")
    private String userRole; // ADMIN / CUSTOMER

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public UserRole() {}

    public UserRole(Integer id, String userRole, String createdAt, String updatedAt) {
        this.id = id;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() { return id; }
    public String getUserRole() { return userRole; }
    public String getCreatedAt() { return createdAt; }
    public String getUpdatedAt() { return updatedAt; }

    public void setId(Integer id) { this.id = id; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
