package com.example.zerowastehubs.dto;

import com.google.gson.annotations.SerializedName;

public class SubCategoryDto {

    @SerializedName("id")
    private Integer id;

    @SerializedName("subCategoryName")
    private String subCategoryName;

    @SerializedName("image")
    private String image;

    @SerializedName("categoryId")
    private Integer categoryId;
    @SerializedName("createdAt")
    private String createdAt; // use String on Android
    public String getCreatedAt() { return createdAt; }
    @SerializedName("updatedAt")
    private String updatedAt; // use String on Android

       public  SubCategoryDto(Integer id,String subCategoryName,String image,Integer categoryId){
                    this.id = id;
                    this.subCategoryName  = subCategoryName;
                    this.image = image;
                    this.categoryId = categoryId;
       }
       public  SubCategoryDto(String subCategoryName,String image,Integer categoryId){
                    this.subCategoryName  = subCategoryName;
                    this.image = image;
                    this.categoryId = categoryId;
       }
       public  SubCategoryDto(String subCategoryName,String image){
                    this.subCategoryName  = subCategoryName;
                    this.image = image;
       }
    public String getUpdatedAt() { return updatedAt; }

    public Integer getId() { return id; }
    public String getSubCategoryName() { return subCategoryName; }
    public String getImage() { return image; }
    public Integer getCategoryId() { return categoryId; }

}
