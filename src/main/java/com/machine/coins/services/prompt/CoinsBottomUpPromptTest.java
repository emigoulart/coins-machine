package com.machine.coins.services.prompt;

import java.util.*;

public class CoinsBottomUpPromptTest {
    public static int coinChange(int[] coins, int amount) {
        int len = coins.length;
        int minCoins = minCoinsBottomUp(coins, amount, len);
        return minCoins != Integer.MAX_VALUE ? minCoins : -1;
    }

    public static int minCoinsBottomUp(int[] denoms, int amount, int len) {
        int[] arr = new int[amount + 1];
        Arrays.fill(arr, amount +1);
        arr[0] = 0;
        //denominations
        for (int i = 0; i < len; i++) {

            for (int j = 1; j < arr.length; j++) {

                if (denoms[i] <= j) {

                    if (1 + arr[j - denoms[i]] < arr[j])

                        arr[j] = 1 + arr[j - denoms[i]];
                }
            }

        }
        return arr[amount];
    }

    public static void main(String[] args) {
        int[] input = {1, 2, 3};
        int amount = 5;
        int res = coinChange(input, amount);

        System.out.println("Result: " + res);
    }
}
