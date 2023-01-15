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
        availableCoins.getCoins().sort(Comparator.comparingDouble(CoinDTO::getCoinValue));// OnlogN
        int i = availableCoins.getCoins().size() - 1;// index of the biggest coin
        while (i >= 0) {
            count = 0;
            var currentCoin = availableCoins.getCoins().get(i);
            while (amount >= currentCoin.getCoinValue() && count < currentCoin.getQuantity()) {//Find the biggest coin that is less than given amount
                count++;
                amount = amount - currentCoin.getCoinValue();//subtract coin from amount
                coins.put(currentCoin.getCoinValue(), count);// store the coin and the quantity
            }
            i--;
        }

        List<CoinDTO> coinsToReturnDTO = new ArrayList<>();
        List<CoinDTO> coinsToUpdate = new ArrayList<>();
        for (Map.Entry<Double, Integer> mCoins : coins.entrySet()) {// loop the stored coins in the map to retrieve the values

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
            coinUpdatable.setQuantity(balance);//updating balance

            coinsToUpdate.add(coinUpdatable);
            change += mCoins.getKey() * mCoins.getValue();
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
