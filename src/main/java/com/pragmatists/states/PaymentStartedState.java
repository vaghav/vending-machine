package com.pragmatists.states;

import com.pragmatists.api.VendingMachineImpl;
import com.pragmatists.distributors.CoinDispenser;
import com.pragmatists.dto.OrderResult;
import com.pragmatists.dto.Product;
import com.pragmatists.enums.Coin;
import com.pragmatists.exceptions.CoinNotAvailableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentStartedState extends GenericMachineState implements MachineState {

    private static final String SUCCESS_MESSAGE = "'" + " ordering made successfully! Thanks";
    private static final String CANCEL_MESSAGE = "Please insert remaining coins or press 'cancel' button";

    public PaymentStartedState(VendingMachineImpl vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectShelf(int shelfNUmber) {
        //TODO: Throw appropriate exception
    }

    @Override
    public OrderResult insertCoin(Coin coin) {

        if (coin == null) {
            machine.setMessage("Please insert coins to vending machine");
            throw new IllegalArgumentException(machine.getDisplayMessage());
        }

        CoinDispenser dispenser = retrieveDispenserByCoin(coin);
        dispenser.setCoinQuantity(dispenser.getCoinQuantity() + 1);
        machine.setInsertedAmount(machine.getInsertedAmount() + coin.getValue());
        machine.setMessage("Coin with value " + "'" + coin.getValue() + "'" + " inserted");

        Product selectedProduct = machine.getSelectedShelf().getProduct();
        float productPrice = selectedProduct.getPrice();

        if (machine.getInsertedAmount() < productPrice) {
            machine.setMessage(CANCEL_MESSAGE);
            return new OrderResult(null, Collections.emptyList());
        } else if (machine.getInsertedAmount() == productPrice) {
            machine.setMessage("'" + selectedProduct.getName() + SUCCESS_MESSAGE);
            return new OrderResult(selectedProduct, Collections.emptyList());
        }

        List<Coin> change;
        try {
            change = calculateChange(machine.getInsertedAmount() - productPrice);
        } catch (CoinNotAvailableException e) {
            return cancelOrder();

        }
        return createOrderResult(selectedProduct, change);
    }


    private CoinDispenser retrieveDispenserByCoin(Coin coin) {
        return machine.getDispenserList().stream()
                .filter(dispenser -> dispenser.getDispenserType() == coin.getValue()).findFirst().get();
    }

    private OrderResult createOrderResult(Product selectedProduct, List<Coin> change) {
        machine.setInsertedAmount(0);
        machine.setMessage("'" + selectedProduct.getName() + SUCCESS_MESSAGE);
        return new OrderResult(selectedProduct, change);
    }

    private List<Coin> calculateChange(float change) throws CoinNotAvailableException {
        List<Coin> changeCoinList = new ArrayList<>();
        machine.getFirstDispenser().dispense(change, changeCoinList);
        return changeCoinList;
    }

    @Override
    public OrderResult cancelOrder() {
        try {
            return new OrderResult(null, calculateChange(machine.getInsertedAmount()));
        } catch (CoinNotAvailableException e) {
            throw new RuntimeException("This will not happen");
        }
    }
}
