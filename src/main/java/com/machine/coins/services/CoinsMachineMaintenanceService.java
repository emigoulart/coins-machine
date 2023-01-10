package com.machine.coins.services;

import com.machine.coins.exceptions.CoinsMachineException;
import com.machine.coins.mapper.CoinsMachineMapper;
import com.machine.coins.repositories.CoinsMachineRepository;
import dto.CoinDTO;
import dto.CoinsMachineDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
public class CoinsMachineMaintenanceService {

    private final CoinsMachineRepository repository;

    private final CoinsMachineValidatorService validatorService;

    @Value("${maxCoins}")
    private int maxCoins;

    public Optional<CoinsMachineDTO> getAvailableCoins() {
        var result = repository.findAll();

        var initialCoins = result.stream().findFirst()
                .map(CoinsMachineMapper::mapToDTO).orElseThrow();

        if (getMaxCoins() != initialCoins.getMaxCoinsPerType()) {
            initialCoins.setMaxCoinsPerType(getMaxCoins());

            for (var coinDTO : initialCoins.getCoins()) {
                 coinDTO.setQuantity(getMaxCoins());
            }

            return Optional.of(saveOrUpdate(initialCoins));
        }
        return result.stream().findFirst()
                .map(CoinsMachineMapper::mapToDTO);
    }

    @Transactional
    public CoinsMachineDTO saveOrUpdate(CoinsMachineDTO coinsMachineDTO) {
        return CoinsMachineMapper.mapToDTO(repository.save(CoinsMachineMapper
                .mapToEntity(coinsMachineDTO)));
    }

    @Transactional
    public CoinsMachineDTO updateCoinsBalance(final Long id, CoinDTO coinsToUpdate) {
        var coinMachine = repository.findById(id).orElseThrow();

        if (coinMachine.getCoins().isEmpty()) {
            throw new CoinsMachineException();
        }

        for (var coins : coinMachine.getCoins()) {
            if (coins.getCoinValue().equals(coinsToUpdate.getCoinValue())) {
                coins.setQuantity(coinsToUpdate.getQuantity());
            }
        }
        return CoinsMachineMapper.mapToDTO(repository.save(coinMachine));
    }


    //Get the quantity value from the database and subtract the current quantity
    public Integer getCurrentBalance(CoinsMachineDTO coinsMachineDTO, CoinDTO coinDTO) {
        Integer currentBalance = null;
        for (var coin: coinsMachineDTO.getCoins()) {
            if (coin.getCoinValue().equals(coinDTO.getCoinValue())) {
                currentBalance = coin.getQuantity() - coinDTO.getQuantity();
            }
        }
        return currentBalance;
    }


}
