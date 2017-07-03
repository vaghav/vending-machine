package com.pragmatists.dto;

public class Product {

    private String name;

    private float price;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
