package com.pragmatists.dto;

import com.pragmatists.enums.Coin;

import java.util.List;

public class OrderResult {

    private Product product;

    private List<Coin> change;

    public OrderResult(Product product, List<Coin> change) {
        this.product = product;
        this.change = change;
    }

    public Product getProduct() {
        return product;
    }

    public List<Coin> getChange() {
        return change;
    }
}
