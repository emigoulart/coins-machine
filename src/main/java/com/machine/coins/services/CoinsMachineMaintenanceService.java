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
public class CoinsMachineMaintenanceService {

    private final CoinsMachineRepository repository;

    private final CoinsMachineValidatorService validatorService;
    @Value("${maxCoins}")
    private int maxCoins;
    public Optional<CoinsMachineDTO> getAvailableCoins() {
        var result = repository.findAll();
        if (result.isEmpty() || isNull(result.get(0).getCoins())) {
            return Optional.of(saveOrUpdate(new CoinsMachineDTO()));
        }
        return result.stream().findFirst()
                .map(CoinsMachineMapper::mapToDTO);
    }

    public CoinsMachineDTO saveOrUpdate(CoinsMachineDTO coinsMachineDTO) {
        if (isNull(coinsMachineDTO.getCoins())) {
            double[] denoms = {0.01, 0.05, 0.10, 0.25};

            List<CoinDTO> listCoins = new ArrayList<>();
            for (double denom : denoms) {
                listCoins.add(CoinDTO.builder()
                        .coinValue(denom)
                        .quantity(maxCoins)
                        .build());
            }
            var coinMachineDTO = CoinsMachineDTO
                    .builder()
                    .maxCoinsPerType(maxCoins)
                    .coins(listCoins)
                    .build();
            var resultCoins = repository.save(CoinsMachineMapper
                    .mapToEntity(coinMachineDTO));

            return CoinsMachineMapper.mapToDTO(resultCoins);

        }
        return CoinsMachineMapper.mapToDTO(repository.save(CoinsMachineMapper
                .mapToEntity(coinsMachineDTO)));
    }
}
