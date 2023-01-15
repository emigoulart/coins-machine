package com.machine.coins.services.prompt;

import io.swagger.models.auth.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoinsMaxNumPromptTest {
    //most amount of coins to make up that amount
    public static int coinChange(int[] denoms, int amount) {
        Arrays.sort(denoms);
        int maxCoins = 20;
        int change=0;
        Map<Integer, Integer> coins = new HashMap<>();
        int numberOfCoins = 0;
        int count;
        int i = 0;// index of the smallest coin
        while (i < denoms.length) {
            count = 0;
            while (amount >= denoms[i] && count < maxCoins) {
                count++;
                amount = amount - denoms[i];
                coins.put(denoms[i], count);
            }
            i++;
        }

        for (Map.Entry<Integer, Integer> mCoins : coins.entrySet()) {
            System.out.print(" Coin(s): " + mCoins.getKey() + " quantity: " + mCoins.getValue());
             change += mCoins.getKey() * mCoins.getValue();
            numberOfCoins += mCoins.getValue();
        }
        System.out.print(" Change: " + change);
        return numberOfCoins;
    }

    public static void main(String[] args) {
        int[] denoms = {1, 2, 5};
        int amount = 50;

        int res = coinChange(denoms, amount);

        System.out.println("\nTotal coins : " + res);
    }
}
