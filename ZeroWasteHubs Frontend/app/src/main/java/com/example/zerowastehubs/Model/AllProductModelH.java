package com.example.zerowastehubs.Model;


public class AllProductModelH {
    private float price;
    private String title;
    private String location;
    private String details;
    private String date;
    private String description;
    private int image;

    // Constructor
    public AllProductModelH(float price, String title, String location, String date, String details, String description) {
        this.price = price;
        this.title = title;
        this.location = location;
        this.date = date;
        this.details = details;
        this.description = description;
    }  // Constructor
    public AllProductModelH(int image,float price, String title, String location, String date, String details, String description) {
        this.image = image;
        this.price = price;
        this.title = title;
        this.location = location;
        this.date = date;
        this.details = details;
        this.description = description;
    }
 public AllProductModelH(float price, String title, String location) {
     this.price = price;
     this.title = title;
     this.location = location;
 }
     public AllProductModelH( String title, int image) {
        this.title = title;
        this.image = image;
    }

    // Empty constructor (needed for Firebase or serialization)


    // Getters
    public float getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }

    public int getImage(){return  image;}

    // Setters
    public void setImage(int image){this.image = image;}
    public void setPrice(float price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
