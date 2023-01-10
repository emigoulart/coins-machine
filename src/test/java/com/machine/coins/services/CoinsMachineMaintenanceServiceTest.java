package com.machine.coins.services;

import com.machine.coins.repositories.CoinsMachineRepository;
import dto.CoinDTO;
import dto.CoinsMachineDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class CoinsMachineMaintenanceServiceTest {

    @Autowired
    private CoinsMachineService service;

    @Autowired
    private CoinsMachineValidatorService validatorService;

    @Autowired
    private CoinsMachineMaintenanceService coinsMachineMaintenanceService;

    @Autowired
    private CoinsMachineRepository repository;

    private Integer maxCoins;

    private CoinsMachineDTO coinsMachineDTO;

    private CoinDTO coinDTO;

    @BeforeEach
    void setUp() throws Exception {
        maxCoins = coinsMachineMaintenanceService.getMaxCoins();
        coinDTO = CoinDTO
                .builder()
                .coinValue(0.25)
                .quantity(50)
                .build();

        double[] denoms = {0.01, 0.05, 0.10, 0.25};

        List<CoinDTO> listCoins = new ArrayList<>();
        for (double denom : denoms) {
            listCoins.add(CoinDTO.builder()
                    .coinValue(denom)
                    .quantity(maxCoins)
                    .build());
        }
        coinsMachineDTO = CoinsMachineDTO
                .builder()
                .id(1L)
                .maxCoinsPerType(maxCoins)
                .coins(listCoins)
                .build();
    }

    @Test
    public void getAvailableCoinsSuccess() {
        var coinsDto = coinsMachineMaintenanceService.getAvailableCoins().orElse(null);
        Assertions.assertNotNull(coinsDto);
        Assertions.assertEquals(coinsDto.getMaxCoinsPerType(), maxCoins);
    }

    @Test
    public void getSaveOrUpdateSuccess() {
        var coinsDto = coinsMachineMaintenanceService.saveOrUpdate(coinsMachineDTO);
        Assertions.assertNotNull(coinsDto);
    }

    @Test
    public void updateCoinsBalanceSuccess() {
        var coinToUpdate = CoinDTO
                .builder()
                .coinValue(0.25)
                .quantity(20)
                .build();

        var coinsDto = coinsMachineMaintenanceService.updateCoinsBalance(1L, coinToUpdate);
        var result = coinsDto.getCoins().stream().filter(coinDTO -> coinToUpdate.getCoinValue()
                .equals(coinDTO.getCoinValue())).findFirst().orElse(null);

        Assertions.assertNotNull(coinsDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getQuantity(), coinToUpdate.getQuantity());

    }

    @Test
    public void updateCoinsBalanceShouldThrowNoSuchElementException() {

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            coinsMachineMaintenanceService.updateCoinsBalance(10L, new CoinDTO());
        });

    }

}
