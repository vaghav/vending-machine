package com.pragmatists.states;

import com.pragmatists.dto.OrderResult;
import com.pragmatists.enums.Coin;

public interface MachineState {

    void selectShelf(int shelfNUmber);

    OrderResult insertCoin(Coin coin);

    OrderResult cancelOrder();

}

