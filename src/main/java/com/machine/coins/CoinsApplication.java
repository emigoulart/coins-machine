package com.machine.coins;

import com.machine.coins.entities.CoinsMachine;
import com.machine.coins.repositories.CoinsMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoinsApplication.class, args);
    }


}
