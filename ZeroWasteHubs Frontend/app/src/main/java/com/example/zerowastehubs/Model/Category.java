package com.example.zerowastehubs.Model;

public class Category {
    private String title;
    private int image;

    public Category(String name, int image) {
        this.title = name;
        this.image = image;
    }
    public String getTitle() { return title; }
    public int getImage() { return image; }
}

