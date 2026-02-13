package com.example.zerowastehubs.entity;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;

public class Product {

    @SerializedName("id")
    private Integer id;

    @SerializedName("image")
    private String image;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("title")
    private String title;

    @SerializedName("details")
    private String details;

    @SerializedName("description")
    private String description;

    @SerializedName("location")
    private String location;

    @SerializedName("category")
    private Category category;

    @SerializedName("subCategory")
    private SubCategory subCategory;

    public Product() {}

    public Product(Integer id, String image, BigDecimal price, String title,
                   String details, String description, String location,
                   Category category, SubCategory subCategory) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.title = title;
        this.details = details;
        this.description = description;
        this.location = location;
        this.category = category;
        this.subCategory = subCategory;
    }

    public Integer getId() { return id; }
    public String getImage() { return image; }
    public BigDecimal getPrice() { return price; }
    public String getTitle() { return title; }
    public String getDetails() { return details; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public Category getCategory() { return category; }
    public SubCategory getSubCategory() { return subCategory; }

    public void setId(Integer id) { this.id = id; }
    public void setImage(String image) { this.image = image; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setTitle(String title) { this.title = title; }
    public void setDetails(String details) { this.details = details; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setCategory(Category category) { this.category = category; }
    public void setSubCategory(SubCategory subCategory) { this.subCategory = subCategory; }
}

