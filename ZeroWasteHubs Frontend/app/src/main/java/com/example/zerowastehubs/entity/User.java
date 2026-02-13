package com.example.zerowastehubs.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class User {

    @SerializedName("id")
    private Integer id;

    @SerializedName("userName")
    private String userName;

    @SerializedName("email")
    private String email;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("userRoles")
    private List<UserRole> userRoles;

    public User() {}

    public User(Integer id, String userName, String email, String phoneNumber, String password, List<UserRole> userRoles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRoles = userRoles;
    }

    public Integer getId() { return id; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public List<UserRole> getUserRoles() { return userRoles; }

    public void setId(Integer id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }
    public void setUserRoles(List<UserRole> userRoles) { this.userRoles = userRoles; }
}

