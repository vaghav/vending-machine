package com.pragmatists.states;

import com.pragmatists.api.VendingMachineImpl;
import com.pragmatists.dto.OrderResult;
import com.pragmatists.enums.Coin;

public class InitialState extends GenericMachineState implements MachineState {

    public InitialState(VendingMachineImpl vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectShelf(int shelfNUmber) {
        machine.setSelectedShelf(machine.getShelves().stream().filter(shelf -> shelf.getShelfNumber() == shelfNUmber)
                .findFirst().get());

        if (machine.getSelectedShelf().getProductQuantity() == 0) {
            machine.setMessage("Selected product doesn't exist in the shelf.Please choose another one");
        }
    }

    @Override
    public OrderResult insertCoin(Coin coin) {
        //TODO: Throw appropriate exception
        return null;
    }

    @Override
    public OrderResult cancelOrder() {
        //TODO: Throw appropriate exception
        return null;
    }
}
