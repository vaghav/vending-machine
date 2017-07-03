package com.pragmatists.distributors;

import com.pragmatists.enums.Coin;
import com.pragmatists.exceptions.CoinNotAvailableException;

import java.util.List;

public interface CoinDispenser {

    void setNextDispenser(CoinDispenser coinDispenser);

    void dispense(float amount, List<Coin> change) throws CoinNotAvailableException;

    int getCoinQuantity();

    void setCoinQuantity(int coinQuantity);

    float getDispenserType();
}
