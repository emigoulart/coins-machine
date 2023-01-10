package com.machine.coins.controllers;

import com.machine.coins.services.CoinsMachineService;
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

    @GetMapping(value = "/{amount}")
    @ApiOperation(value = "Return coins change for a given amount.",
            notes = "Return coins change for a given amount in Bills that can be (1,2,5,10,20,50,100).")
    public ResponseEntity<CoinsMachineDTO> getChange(@PathVariable double amount) {

        return ResponseEntity.ok(coinsMachineService.getCoinsChange(amount));

    }
}
