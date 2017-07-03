package com.pragmatists.distributors;

import com.pragmatists.enums.Coin;
import com.pragmatists.exceptions.CoinNotAvailableException;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CoinDispenserImpl implements CoinDispenser {

    private static final int PRECISION = 2;
    protected CoinDispenser dispenser;

    private float dispenserType;

    private int coinQuantity;

    public CoinDispenserImpl(float dispenserType) {
        this.dispenserType = dispenserType;
    }

    @Override
    public void setNextDispenser(CoinDispenser coinDispenser) {
        this.dispenser = coinDispenser;
    }

    @Override
    public void dispense(float amount, List<Coin> dispensedCoinList) throws CoinNotAvailableException {
        if(amount >= dispenserType)  {
            int quotient = (int)(amount / dispenserType);
            float reminder = amount % dispenserType;
            Coin coin = selectCoinWithValue(dispenserType);

            if (coinQuantity == 0) {
                throw new CoinNotAvailableException("Couldn't return change");
            }

            System.out.println("Dividing " + quotient + "---->" + " '" + coin + "' coins");
            IntStream.range(1, quotient + 1).forEach(value -> dispensedCoinList.add(coin));

            if (reminder != 0) {
                dispenser.dispense(round(reminder), dispensedCoinList);
            }
        } else {
            dispenser.dispense(round(amount ), dispensedCoinList);
        }
    }

    private float round(float changeAmount) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(PRECISION);
        return Float.valueOf(df.format(changeAmount));
    }

    private Coin selectCoinWithValue(float value) {
        return Arrays.stream(Coin.values()).filter(coin -> coin.getValue() == value).findFirst().get();
    }

    public int getCoinQuantity() {
        return coinQuantity;
    }

    @Override
    public void setCoinQuantity(int coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

    @Override
    public float getDispenserType() {
        return dispenserType;
    }
}
