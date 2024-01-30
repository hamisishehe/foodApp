package com.example.fooddelivery;

public class FoodModel {
    public int food_id, price;
    public String name, imageurl;

    public FoodModel(int food_id, String name, int price, String imageurl) {
        this.food_id = food_id;
        this.name = name;
        this.price = price;
        this.imageurl = imageurl;
    }


    public int getId() {
        return food_id;
    }

    public void setId(int id) {
        this.food_id = food_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
