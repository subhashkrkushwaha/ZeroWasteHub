package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;


public class WishlistDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("productId")
    private Integer productId;

    @SerializedName("createdAt")
    private String createdAt;  // String for Android
}
