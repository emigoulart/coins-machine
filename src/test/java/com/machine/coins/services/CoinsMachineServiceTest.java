package com.machine.coins.services;


import com.machine.coins.exceptions.CoinsMachineException;
import com.machine.coins.repositories.CoinsMachineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CoinsMachineServiceTest {

    @Autowired
    private CoinsMachineService service;

    @Autowired
    private CoinsMachineValidatorService validatorService;

    @Autowired
    private CoinsMachineMaintenanceService coinsMachineMaintenanceService;

    @Autowired
    private CoinsMachineRepository repository;

    private Double amount;

    @BeforeEach
    void setUp() throws Exception {
        amount = 2D;

    }

    @Test
    public void getCoinsChangeSuccess() {

        var coinsDto = service.getCoinsChange(amount);

        Assertions.assertNotNull(coinsDto);
        Assertions.assertEquals(amount, coinsDto.getAmount());
    }

    @Test
    public void getCoinsChangeShouldThrowCoinsMachineExceptionWhenThereIsNoEnoughCoins() {
        var message = "There is no enough coins to complete this change," +
                " please try with another bill";

        var exception = Assertions.assertThrows(CoinsMachineException.class, () -> {
            service.getCoinsChange(100);
        });

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void getCoinsChangeShouldThrowCoinsMachineExceptionWhenBillIsNotValid() {
        var message = "Invalid bill." +
                " Please, make sure to provide a bill contained in this set: 1,2,5,10,20,50,100.";

        var exception = Assertions.assertThrows(CoinsMachineException.class, () -> {
            service.getCoinsChange(30);
        });

        Assertions.assertEquals(message, exception.getMessage());
    }


}
