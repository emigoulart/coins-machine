package com.machine.coins.repositories;

import com.machine.coins.entities.CoinsMachine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinsMachineRepository extends JpaRepository<CoinsMachine, Long> {


}