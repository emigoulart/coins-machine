package com.machine.coins.services;

import com.machine.coins.exceptions.CoinsMachineException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinsMachineValidatorService {

    public void validate(final double amount) {
        List<Double> listBills = List.of(1D,2D,5D,10D,20D,50D,100D);

        if (!listBills.contains(amount)){
            throw new CoinsMachineException("Invalid bill." +
                    " Please, make sure to provide a bill contained in this set: 1,2,5,10,20,50,100.");
        }
    }

    public void validate(final double originalAmount, final double change) {
        if (originalAmount > change) {
            throw new CoinsMachineException("There is no enough coins to complete this change," +
                    " please try with another bill");
        }
    }
}
