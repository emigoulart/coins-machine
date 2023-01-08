package com.machine.coins.controllers;

import com.machine.coins.exceptions.CoinsMachineException;
import com.machine.coins.services.CoinsMachineMaintenanceService;
import com.machine.coins.services.CoinsMachineService;
import dto.CoinDTO;
import dto.CoinsMachineDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/coins")
@RequiredArgsConstructor
public class CoinsMachineController {

    private final CoinsMachineService coinsMachineService;

    private final CoinsMachineMaintenanceService maintenanceService;

    @GetMapping(value = "/{amount}")
    @ApiOperation(value = "Return coins change for a given amount.",
            notes = "Return coins change for a given amount in Bills that can be (1,2,5,10,20,50,100).")
    public ResponseEntity<CoinsMachineDTO> getChange(@PathVariable Double amount) {
        var coinsDTO = maintenanceService.getAvailableCoins();

        double[] coins = coinsDTO.map(CoinsMachineDTO::getCoins)
                .orElseThrow(() -> new CoinsMachineException("There is no enough coins to" +
                        " complete this transaction."))
                .stream().mapToDouble(CoinDTO::getCoinValue).toArray();
        var maxCoins = coinsDTO.map(CoinsMachineDTO::getMaxCoinsPerType).orElse(null);
        return ResponseEntity.ok(coinsMachineService.getCoinsChange(coins, amount, maxCoins));

    }
}
