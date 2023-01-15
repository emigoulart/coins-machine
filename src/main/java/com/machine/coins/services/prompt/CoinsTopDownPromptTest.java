package com.machine.coins.services.prompt;

import java.util.HashMap;

public class CoinsTopDownPromptTest {
    public static int coinChange(double[] coins, double amount) {
        int size = coins.length;
        int minCoins = minCoinsTopDown(coins, size, amount, new HashMap<>());
        return minCoins != Integer.MAX_VALUE ? minCoins : -1;
    }

    //Top-down approach
    private static int minCoinsTopDown(double[] coins, int size, double total, HashMap<Double, Integer> hashMap) {

        if (total == 0)
            return 0;

        if (hashMap.containsKey(total))
            return hashMap.get(total);

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (coins[i] <= total) {
                int subResult = minCoinsTopDown(coins, size, total - coins[i], hashMap);
                if (subResult != Integer.MAX_VALUE && subResult + 1 < result)
                    result = subResult + 1;
            }
        }

        hashMap.put(total, result);
        return result;
    }

    public static void main(String[] args) {
        double[] dCoins = {0.01, 0.05, 0.10, 0.25};
        double amount = 1;
        int res = coinChange(dCoins, amount);

        System.out.println("Result: " + res);
    }
}
