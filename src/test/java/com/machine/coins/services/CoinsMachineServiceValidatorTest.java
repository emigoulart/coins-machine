package com.machine.coins.services;


import com.machine.coins.exceptions.CoinsMachineException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CoinsMachineServiceValidatorTest {

    @Autowired
    private CoinsMachineValidatorService validatorService;

    private double amount;

    @BeforeEach
    void setUp() throws Exception {
        amount = 200D;
    }

    @Test
    public void validateShouldPass() {
       Assertions.assertDoesNotThrow(() -> validatorService.validate(1D));

    }

    @Test
    public void validateShouldThrowInvalidBill() {
        var message = "Invalid bill." +
                " Please, make sure to provide a bill contained in this set: 1,2,5,10,20,50,100.";

        var exception = Assertions.assertThrows(CoinsMachineException.class, () -> {
            validatorService.validate(amount);
        });

        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    public void validateShouldThrowException() {
        var message = "There is no enough coins to complete this change," +
                " please try with another bill";

        var exception = Assertions.assertThrows(CoinsMachineException.class, () -> {
            validatorService.validate(50L, 20L);
        });

        Assertions.assertEquals(message, exception.getMessage());
    }

}
