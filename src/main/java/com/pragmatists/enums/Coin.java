package com.pragmatists.enums;

public enum Coin {
    FIVE(5f), TWO(2f), ONE(1f), ZERO_FIVE(0.5f), ZER_TWO(0.2f), ZERO_ONE(0.1f);

    private float value;

    Coin(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
