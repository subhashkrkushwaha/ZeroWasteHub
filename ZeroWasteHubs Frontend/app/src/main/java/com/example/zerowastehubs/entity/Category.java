package com.example.zerowastehubs.entity;


import com.google.gson.annotations.SerializedName;


public class Category {

    @SerializedName("id")
    private Integer id;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("imagePath")
    private String imagePath;

    public Category() {}

    public Category(Integer id, String categoryName, String imagePath) {
        this.id = id;
        this.categoryName = categoryName;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}


