package com.example.zerowastehubs.entity;

import com.google.gson.annotations.SerializedName;

public class Wishlist {

    @SerializedName("id")
    private Integer id;

    @SerializedName("user")
    private User user;

    @SerializedName("product")
    private Product product;

    @SerializedName("createdAt")
    private String createdAt;   // LocalDateTime → String in Android

    public Wishlist() {}

    public Wishlist(Integer id, User user, Product product, String createdAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public Product getProduct() { return product; }
    public String getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProduct(Product product) { this.product = product; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
