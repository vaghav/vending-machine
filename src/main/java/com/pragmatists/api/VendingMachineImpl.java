package com.pragmatists.api;

import com.pragmatists.distributors.CoinDispenser;
import com.pragmatists.distributors.CoinDispenserImpl;
import com.pragmatists.dto.OrderResult;
import com.pragmatists.dto.Shelf;
import com.pragmatists.enums.Coin;
import com.pragmatists.exceptions.CoinNotAvailableException;
import com.pragmatists.states.InitialState;
import com.pragmatists.states.MachineState;
import com.pragmatists.states.PaymentStartedState;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class VendingMachineImpl implements VendingMachine {

    private List<Shelf> shelfList;

    private List<CoinDispenser> dispenserList;

    private String displayMessage;

    private Shelf selectedShelf;

    private float insertedAmount;

    private MachineState machineState;

    private CoinDispenser firstDispenser;

    public VendingMachineImpl(List<Shelf> shelfList, Map<Coin, Integer> deposit) {
        if (shelfList == null || shelfList.isEmpty()) {
            setMessage("Please insert coins to vending machine");
            throw new IllegalArgumentException(displayMessage);
        }
        this.shelfList = shelfList;
        this.dispenserList = createCoinDispenserChain();
        this.firstDispenser = dispenserList.get(0);
        machineState = new InitialState(this);
        chargeDispansers(deposit);
    }

    private void chargeDispansers(Map<Coin, Integer> deposit) {
        deposit.entrySet().stream().forEach(entry -> retrieveDispenserByCoin(entry.getKey()).setCoinQuantity(entry.getValue()));
    }

    private CoinDispenser retrieveDispenserByCoin(Coin coin) {
        return dispenserList.stream()
                .filter(dispenser -> dispenser.getDispenserType() == coin.getValue()).findFirst().get();
    }

    public VendingMachineImpl(List<Shelf> shelfList) {
        this(shelfList, new HashMap<>());
    }

    private List<CoinDispenser> createCoinDispenserChain() {
        List<CoinDispenser> dispensers = new ArrayList<>();
        List<Float> coinSortedValues = getCoinsSortedValues();
        CoinDispenser dispenser = new CoinDispenserImpl(coinSortedValues.get(0));
        dispensers.add(dispenser);

        for (int i = 0; i < Coin.values().length - 1; i++) {
            CoinDispenser nextDispenser = new CoinDispenserImpl(coinSortedValues.get(i + 1));
            dispensers.add(nextDispenser);
            dispenser.setNextDispenser(nextDispenser);
            dispenser = nextDispenser;
        }
        return dispensers;
    }

    private List<Float> getCoinsSortedValues() {
        return Arrays.stream(Coin.values()).map(coin -> coin.getValue())
                .sorted(Comparator.reverseOrder()).collect(toList());
    }

    @Override
    public void selectShelf(int shelfNUmber) {
        selectedShelf = shelfList.stream().filter(shelf -> shelf.getShelfNumber() == shelfNUmber)
                .findFirst().get();

        if (selectedShelf.getProductQuantity() == 0) {
            setMessage("Selected product doesn't exist in the shelf.Please choose another one");
        }
    }

    @Override
    public OrderResult insertCoin(Coin coin) {
        machineState = new PaymentStartedState(this);
        return machineState.insertCoin(coin);
    }

    public void setMessage(String displayMessage) {
        this.displayMessage = displayMessage;
        System.out.println(this.displayMessage);
    }

    private List<Coin> calculateChange(float change) throws CoinNotAvailableException {
        List<Coin> changeCoinList = new ArrayList<>();
        firstDispenser.dispense(change, changeCoinList);
        return changeCoinList;
    }

    @Override
    public OrderResult cancelOrder() {
        machineState = new PaymentStartedState(this);
        return machineState.cancelOrder();
    }

    @Override
    public List<Shelf> getShelves() {
        return shelfList;
    }

    public Shelf getSelectedShelf() {
        return selectedShelf;
    }

    public float getInsertedAmount() {
        return insertedAmount;
    }

    public void setInsertedAmount(float insertedAmount) {
        this.insertedAmount = insertedAmount;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public List<CoinDispenser> getDispenserList() {
        return dispenserList;
    }

    public CoinDispenser getFirstDispenser() {
        return firstDispenser;
    }

    public void setSelectedShelf(Shelf selectedShelf) {
        this.selectedShelf = selectedShelf;
    }
}
