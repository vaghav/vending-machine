package com.pragmatists.states;

import com.pragmatists.api.VendingMachineImpl;

public class GenericMachineState {

    protected VendingMachineImpl machine;

    public GenericMachineState(VendingMachineImpl vendingMachine) {
        this.machine = vendingMachine;
    }
}
