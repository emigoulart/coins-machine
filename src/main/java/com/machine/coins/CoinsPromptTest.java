package com.machine.coins;

import com.machine.coins.exceptions.CoinsMachineException;

import java.util.*;

public class CoinsPromptTest {
    static int maxCoins = 100;

    static int coinChange(double[] denoms, double amount) {
        double originalAmount = amount;
        Arrays.sort(denoms);
        double change = 0;
        Map<Double, Integer> coins = new HashMap<>();
        int numberOfCoins = 0;
        int count;
        int i = denoms.length - 1;// index of the biggest coin
        while (i >= 0) {
            count = 0;
            while (amount >= denoms[i] && count < maxCoins) {//Find the biggest coin that is less than given amount
                count++;
                amount = amount - denoms[i];
                coins.put(denoms[i], count);
            }
            i--;
        }
        for (Map.Entry<Double, Integer> mCoins : coins.entrySet()) {
            System.out.print(" Coin(s): " + mCoins.getKey() + " quantity: " + mCoins.getValue());
            change = +mCoins.getKey() * mCoins.getValue();
            numberOfCoins += mCoins.getValue();
        }
        if (originalAmount > change) {
            System.out.print("\nThere is no enough coins for this change: " + change);
        }
        System.out.print(" Change: --> " + change);
        return numberOfCoins;
    }

    public static void main(String[] args) {
        double[] dCoins = {0.01, 0.05, 0.10, 0.25};
        Scanner input = new Scanner(System.in);
        System.out.printf("Enter amount, available bills {1,2,5,10,20,50,100} : ");
        double amount = input.nextDouble();

        List<Double> listBills = List.of(1D, 2D, 5D, 10D, 20D, 50D, 100D);

        while (!listBills.contains(amount)) {
            System.out.println("Invalid bill." +
                    " Please, make sure to provide a bill contained in this set: 1,2,5,10,20,50,100.");
            input = new Scanner(System.in);
            System.out.printf("Enter amount, available bills {1,2,5,10,20,50,100} : ");
            amount = input.nextDouble();
        }
        var res = coinChange(dCoins, amount);
        System.out.println("\n--> Number of coins: " + res);
    }
}
