package com.machine.coins.mapper;

import com.machine.coins.entities.Coin;
import com.machine.coins.entities.CoinsMachine;
import dto.CoinDTO;
import dto.CoinsMachineDTO;

import java.util.ArrayList;
import java.util.List;

public class CoinsMachineMapper {

    public static CoinsMachineDTO mapToDTO(CoinsMachine coinsMachine) {
        return CoinsMachineDTO
                .builder()
                .coins(getCoinsDTOList(coinsMachine.getCoins()))
                .maxCoinsPerType(coinsMachine.getMaxCoinsPerType())
                .build();
    }

    public static CoinDTO mapToDTO(Coin coin) {
        return CoinDTO
                .builder()
                .coinValue(coin.getCoinValue())
                .quantity(coin.getQuantity())
                .build();
    }

    public static Coin mapToEntity(CoinDTO coinDto) {
        return Coin
                .builder()
                .coinValue(coinDto.getCoinValue())
                .quantity(coinDto.getQuantity())
                .build();
    }

    public static CoinsMachine mapToEntity(CoinsMachineDTO coinsMachineDTO) {
        return CoinsMachine
                .builder()
                .coins(getCoinsList(coinsMachineDTO.getCoins()))
                .maxCoinsPerType(coinsMachineDTO.getMaxCoinsPerType())
                .build();
    }

    private static List<CoinDTO> getCoinsDTOList(List<Coin> coins) {
        List<CoinDTO> coinDTOList = new ArrayList<>();
        for (var coin : coins) {
            coinDTOList.add(mapToDTO(coin));
        }
        return coinDTOList;
    }

    private static List<Coin> getCoinsList(List<CoinDTO> coinsDTO) {
        List<Coin> coinList = new ArrayList<>();
        for (var coinDTO : coinsDTO) {
            coinList.add(mapToEntity(coinDTO));
        }
        return coinList;
    }
}
