package com.machine.coins.services;

import com.machine.coins.exceptions.CoinsMachineException;
import com.machine.coins.repositories.CoinsMachineRepository;
import dto.CoinDTO;
import dto.CoinsMachineDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class CoinsMachineService {

    private final CoinsMachineRepository repository;

    private final CoinsMachineValidatorService validatorService;

    private final CoinsMachineMaintenanceService maintenanceService;

    public CoinsMachineDTO getCoinsChange(double amount) {

        log.info("Given amount {}:", amount);
        validatorService.validate(amount);

        var availableCoins = maintenanceService.getAvailableCoins()
                .orElseThrow(CoinsMachineException::new);

        final double originalAmount = amount;
        double change = 0;
        Map<Double, Integer> coins = new HashMap<>();
        int numberOfCoins = 0;
        int count;
        int i = availableCoins.getCoins().size() - 1;
        while (i >= 0) {
            count = 0;
            var currentCoin = availableCoins.getCoins().get(i);
            while (amount >= currentCoin.getCoinValue() && count < currentCoin.getQuantity()) {
                count++;
                amount = amount - currentCoin.getCoinValue();
                coins.put(currentCoin.getCoinValue(), count);
            }
            i--;
        }

        List<CoinDTO> coinsToReturnDTO = new ArrayList<>();
        List<CoinDTO> coinsToUpdate = new ArrayList<>();
        for (Map.Entry<Double, Integer> mCoins : coins.entrySet()) {

            log.info("Coin {}: and quantity {}: ", mCoins.getKey(), mCoins.getValue());
            coinsToReturnDTO.add(CoinDTO.
                    builder()
                    .id(availableCoins.getId())
                    .coinValue(mCoins.getKey())
                    .quantity(mCoins.getValue())
                    .build());

            var coinUpdatable = CoinDTO.
                    builder()
                    .coinValue(mCoins.getKey())
                    .quantity(mCoins.getValue())
                    .build();

            final Integer balance = maintenanceService.getCurrentBalance(availableCoins, coinUpdatable);
            coinUpdatable.setQuantity(balance);

            coinsToUpdate.add(coinUpdatable);
            change = +mCoins.getKey() * mCoins.getValue();
            numberOfCoins += mCoins.getValue();
        }

        if (originalAmount > change) {
            log.info("There is no enough coins for this change {}:", change);
        }
        validatorService.validate(originalAmount, change);

        var coinsData = CoinsMachineDTO.builder()
                .id(availableCoins.getId())
                .numberOfCoins(numberOfCoins)
                .amount(originalAmount)
                .maxCoinsPerType(availableCoins.getMaxCoinsPerType())
                .coins(coinsToReturnDTO)
                .change(change)
                .build();

        for (var changedCoins : coinsToUpdate) {
            maintenanceService.updateCoinsBalance(availableCoins.getId(), changedCoins);
        }
        return coinsData;
    }
}
