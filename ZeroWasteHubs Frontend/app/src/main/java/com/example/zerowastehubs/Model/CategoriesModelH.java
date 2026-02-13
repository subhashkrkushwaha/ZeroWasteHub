package com.example.zerowastehubs.Model;

public class CategoriesModelH {
    private String title;
    private int image;
    public CategoriesModelH(String title, int image){
        this.title = title;
        this.image = image;
    }
    // Empty constructor
    public CategoriesModelH(){}
    public String getTitle(){
        return title;
    }
    public void setTitle(){
        this.title=title;
    }
    public int getImage(){
        return image;
    }
    public void setImage(){
        this.image=image;
    }
}
