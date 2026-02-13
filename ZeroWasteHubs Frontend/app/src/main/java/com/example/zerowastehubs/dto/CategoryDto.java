package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("categoryName")
    private String categoryName;

    @SerializedName("imagePath")
    private String imagePath;
    @SerializedName("createdAt")
    private String createdAt; // use String on Android
    public String getCreatedAt() { return createdAt; }
    @SerializedName("updatedAt")
    private String updatedAt; // use String on Android
    public String getUpdatedAt() { return updatedAt; }

    public CategoryDto() {}
    public CategoryDto(Integer id,String categoryName,String imagePath) {
                  this.id =id;
                  this.categoryName=categoryName;
                  this.imagePath=imagePath;
    }
    public CategoryDto(String categoryName,String imagePath) {
                  this.categoryName=categoryName;
                  this.imagePath=imagePath;
    }
    public CategoryDto(Integer id,String categoryName,String imagePath,String createdAt,String updatedAt) {
        this.id =id;
        this.categoryName=categoryName;
        this.imagePath=imagePath;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

}
