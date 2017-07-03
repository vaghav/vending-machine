package com.pragmatists.helpers;

import com.pragmatists.api.VendingMachine;
import com.pragmatists.api.VendingMachineImpl;
import com.pragmatists.dto.OrderResult;
import com.pragmatists.dto.Shelf;
import com.pragmatists.enums.Coin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VendingMachineTestHelper {

    protected static OrderResult makeProductOrder(int shelfNumber, List<Coin> paidAmount, List<Shelf> shelfList) {
        return makeOrder(() -> {
            return getOrderResult(shelfNumber, paidAmount, shelfList, null);
        });
    }

    protected static OrderResult makeProductOrder(int shelfNumber, List<Coin> paidAmount, List<Shelf> shelfList,
                                        HashMap<Coin, Integer> deposit) {
        return makeOrder(() -> {
            return getOrderResult(shelfNumber, paidAmount, shelfList, deposit);
        });
    }

        protected static OrderResult makeProductOrderWithCancelOperation(int shelfNumber, List<Coin> paidAmount, List<Shelf> shelfList) {
        return makeOrder(() -> {
            return getOrderResultAfterCancel(shelfNumber, paidAmount, shelfList);
        });
    }

    private static OrderResult getOrderResultAfterCancel(int shelfNumber, List<Coin> paidAmount, List<Shelf> shelfList) {
        VendingMachine machine = new VendingMachineImpl(shelfList);
        processOrdering(shelfNumber, paidAmount, machine);
        return machine.cancelOrder();
    }

    private static OrderResult getOrderResult(int shelfNumber, List<Coin> paidAmount, List<Shelf> shelfList,
                                       HashMap<Coin, Integer> deposit) {
        VendingMachine machine;
        if (deposit != null) {
            machine = new VendingMachineImpl(shelfList, deposit);
        } else {
            machine = new VendingMachineImpl(shelfList);
        }
        return processOrdering(shelfNumber, paidAmount, machine);
    }

    private static OrderResult processOrdering(int shelfNumber, List<Coin> paidAmount, VendingMachine machine) {
        OrderResult orderResult = null;
        machine.selectShelf(shelfNumber);
        for (Coin coin : paidAmount) {
            orderResult = machine.insertCoin(coin);
        }
        return orderResult;
    }

    private static OrderResult makeOrder(ExceptionHandlerHelper handlerHelper) {
        try {
            return handlerHelper.handleException();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            System.out.println("Machine can't process order. Please try again later");
            return new OrderResult(null, Collections.emptyList());
        }
    }
}
