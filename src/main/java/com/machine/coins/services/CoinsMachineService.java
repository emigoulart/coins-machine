package com.machine.coins.services;

import com.machine.coins.mapper.CoinsMachineMapper;
import com.machine.coins.repositories.CoinsMachineRepository;
import dto.CoinDTO;
import dto.CoinsMachineDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class CoinsMachineService {

    private final CoinsMachineRepository repository;

    private final CoinsMachineValidatorService validatorService;

    private final CoinsMachineMaintenanceService maintenanceService;

    public CoinsMachineDTO getCoinsChange(double[] denoms, double amount, int maxCoins) {
        log.info("Given amount {}: and coins {}:", amount, denoms);
        validatorService.validate(amount);
        Arrays.sort(denoms);
        double originalAmount = amount;
        double change = 0;
        Map<Double, Integer> coins = new HashMap<>();
        int numberOfCoins = 0;
        int count;
        int i = denoms.length - 1;
        while (i >= 0) {
            count = 0;
            while (amount >= denoms[i] && count < maxCoins) {
                count++;
                amount = amount - denoms[i];
                coins.put(denoms[i], count);
            }
            i--;
        }
        List<CoinDTO> coinsDTO = new ArrayList<>();
        for (Map.Entry<Double, Integer> mCoins : coins.entrySet()) {

            log.info("Coin {}: and quantity {}: ", mCoins.getKey(), mCoins.getValue());
            coinsDTO.add(CoinDTO.
                    builder()
                    .coinValue(mCoins.getKey())
                    .quantity(mCoins.getValue())
                    .build());
            change = +mCoins.getKey() * mCoins.getValue();
            numberOfCoins += mCoins.getValue();

        }

        if (originalAmount > change) {
            log.info("There is no enough coins for this change {}:", change);
        }
        validatorService.validate(originalAmount, change);

        return CoinsMachineDTO.builder()
                .numberOfCoins(numberOfCoins)
                .amount(originalAmount)
                .maxCoinsPerType(maxCoins)
                .coins(coinsDTO)
                .change(change)
                .build();
    }

}
