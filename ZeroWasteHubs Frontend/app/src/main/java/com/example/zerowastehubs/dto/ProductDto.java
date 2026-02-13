package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;


public class ProductDto {

    private Integer id;
    private String image;
    private BigDecimal price;
    private String title;
    private String details;
    private String description;
    private String location;

    private Integer categoryId;

    private Integer subCategoryId;
    @SerializedName("createdAt")
    private String createdAt; // use String on Android
    public String getCreatedAt() { return createdAt; }
    @SerializedName("updatedAt")
    private String updatedAt; // use String on Android
    public String getUpdatedAt() { return updatedAt; }

    public  ProductDto(Integer id ,String image,BigDecimal price,String title,String details,String description,
                       String location,Integer categoryId,Integer subCategoryId,String createdAt,String updatedAt){
              this.id = id;
              this.image = image;
              this.price = price;
              this.title = title;
              this.details = details;
              this.description = description;
              this.location = location;
              this.categoryId = categoryId;
              this.subCategoryId = subCategoryId;
              this.createdAt = createdAt;
              this.updatedAt = updatedAt;
    }
    public  ProductDto(String image,BigDecimal price,String title,String details,String description,
                       String location,Integer categoryId,Integer subCategoryId){
              this.image = image;
              this.price = price;
              this.title = title;
              this.details = details;
              this.description = description;
              this.location = location;
              this.categoryId = categoryId;
              this.subCategoryId = subCategoryId;

    }
    public  ProductDto(String image,BigDecimal price,String title,String location){
              this.image = image;
              this.price = price;
              this.title = title;
              this.location = location;
    }
    // --- GETTERS / SETTERS ---
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getSubCategoryId() { return subCategoryId; }
    public void setSubCategoryId(Integer subCategoryId) { this.subCategoryId = subCategoryId; }
}
