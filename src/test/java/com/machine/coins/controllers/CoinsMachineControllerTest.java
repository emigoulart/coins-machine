package com.machine.coins.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CoinsMachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Integer amount;

    private Integer change;

    @BeforeEach
    void setUp() throws Exception {
        amount = 2;
        change = 2;
    }

    @Test
    public void getCoinsChange() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/coins/" + amount)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.amount").value(amount));
        result.andExpect(jsonPath("$.change").value(change));
    }

}
