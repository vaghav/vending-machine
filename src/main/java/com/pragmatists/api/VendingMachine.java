package com.pragmatists.api;

import com.pragmatists.dto.OrderResult;
import com.pragmatists.dto.Shelf;
import com.pragmatists.enums.Coin;

import java.util.List;

public interface VendingMachine {

    void selectShelf(int shelfNUmber);

    OrderResult insertCoin(Coin coin);

    OrderResult cancelOrder();

    List<Shelf> getShelves();
}
