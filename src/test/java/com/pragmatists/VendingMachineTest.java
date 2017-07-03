package com.pragmatists;

import com.pragmatists.dto.OrderResult;
import com.pragmatists.dto.Product;
import com.pragmatists.dto.Shelf;
import com.pragmatists.enums.Coin;
import com.pragmatists.helpers.VendingMachineTestHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class VendingMachineTest extends VendingMachineTestHelper {

    @Test
    public void shouldReturnResultOrderWithProductAndChange() {
        //given-when
        OrderResult orderResult = makeProductOrder(2, Arrays.asList(Coin.ONE, Coin.ONE, Coin.ONE),
                setUpShelfList());

        //then
        assertThat(orderResult.getProduct().getName()).isEqualTo("water");
        makeAssertions(orderResult, 1, Arrays.asList(Coin.ONE));
    }

    @Test
    public void shouldReturnResultOrderWithProductAndChangeWithGivenMachineDeposit() {
        //given-when
        OrderResult orderResult = makeProductOrder(1, Arrays.asList(Coin.FIVE, Coin.FIVE),
                setUpShelfList(), new HashMap<Coin, Integer>(){{put(Coin.FIVE, 1); put(Coin.ONE, 1);
                    put(Coin.ZERO_FIVE, 1); put(Coin.ZER_TWO, 2);}});

        //then
        assertThat(orderResult.getProduct().getName()).isEqualTo("cola");
        makeAssertions(orderResult, 3, Arrays.asList(Coin.ONE, Coin.ZERO_FIVE, Coin.ZER_TWO));
    }

    @Test
    public void shouldReturnResultOrderWithProductWithoutChange() {
        //given-when
        OrderResult orderResult = makeProductOrder(1, Arrays.asList(Coin.FIVE, Coin.TWO,
                Coin.ONE, Coin.ZER_TWO, Coin.ZERO_ONE), setUpShelfList(), new HashMap<>());

        //then
        assertThat(orderResult).isNotNull();
        assertThat(orderResult.getProduct().getName()).isEqualTo("cola");
        assertThat(orderResult.getChange()).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    public void shouldReturnResultOrderWhenUserCancelTheOrder() {
        //given-when
        OrderResult orderResult = makeProductOrderWithCancelOperation(2, Arrays.asList(Coin.ZERO_ONE),
                setUpShelfList());

        //then
        assertThat(orderResult.getProduct()).isEqualTo(null);
        makeAssertions(orderResult, 1, Arrays.asList(Coin.ZERO_ONE));
    }

    @Test
    public void shouldReturnResultWhenVendingMachineNotHasEnoughCoins() {
        //given-when
        OrderResult orderResult = makeProductOrder(1, Arrays.asList(Coin.FIVE, Coin.FIVE),
                setUpShelfList());

        //then
        assertThat(orderResult.getProduct()).isEqualTo(null);
        makeAssertions(orderResult, 2, Arrays.asList(Coin.FIVE, Coin.FIVE));
    }

    private void makeAssertions(OrderResult orderResult, int size, List<Coin> coinList) {
        assertThat(orderResult).isNotNull();
        assertThat(orderResult.getChange().size()).isEqualTo(size);
        assertThat(orderResult.getChange()).isEqualTo(coinList);
    }

    @Test
    public void shouldReturnOrderResultExceptionalCase() {
        //given-when
        OrderResult orderResult = makeProductOrder(1, Arrays.asList(Coin.FIVE, Coin.TWO,
                Coin.ONE, Coin.ZER_TWO, Coin.ZERO_ONE), null, new HashMap<>());

        //then
        assertThat(orderResult).isNotNull();
        assertThat(orderResult.getChange()).isEqualTo(Collections.EMPTY_LIST);
    }

    private List<Shelf> setUpShelfList() {
        return Arrays.asList(new Shelf(1, new Product("cola", 8.3f), 7),
                        new Shelf(2, new Product("water", 2), 5),
                        new Shelf(3, new Product("chewingGum", 0.1f), 6),
                        new Shelf(4, new Product("sweet", 0.2f), 6));
    }
}
