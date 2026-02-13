package com.example.zerowastehubs.entity;

import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("id")
    private Integer id;

    @SerializedName("subCategoryName")
    private String subCategoryName;

    @SerializedName("image")
    private String image;

    @SerializedName("category")
    private Category category;

    public SubCategory() {}

    public SubCategory(Integer id, String subCategoryName, String image, Category category) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.image = image;
        this.category = category;
    }

    public Integer getId() { return id; }
    public String getSubCategoryName() { return subCategoryName; }
    public String getImage() { return image; }
    public Category getCategory() { return category; }

    public void setId(Integer id) { this.id = id; }
    public void setSubCategoryName(String subCategoryName) { this.subCategoryName = subCategoryName; }
    public void setImage(String image) { this.image = image; }
    public void setCategory(Category category) { this.category = category; }
}

