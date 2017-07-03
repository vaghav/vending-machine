package com.pragmatists.dto;

public class Shelf {

    private int shelfNumber;

    private Product product;

    private int productQuantity;

    public Shelf(int shelfNumber, Product product, int quantity) {
        this.shelfNumber = shelfNumber;
        this.product = product;
        this.productQuantity = quantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }
}
