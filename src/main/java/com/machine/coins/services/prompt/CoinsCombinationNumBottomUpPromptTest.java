package com.machine.coins.services.prompt;

public class CoinsCombinationNumBottomUpPromptTest {
    //number of combinations to make up that amount
    public static int coinChange(int[] denoms, int amount) {

        int[] arr = new int[amount + 1];
        arr[0] = 1;

        for (int coin_column : denoms) {// loop the length of coins array
            for (int i_row = coin_column; i_row <= amount; i_row++) {
                arr[i_row] += arr[i_row - coin_column];
            }
        }

        return arr[amount];
    }
    //           0   1   2   3   4
    // no coin | 0 | 0 | 0 | 0 | 0
    // Only 1  | 1 | 1 | 1 | 1 | 1
    // 1 and 2 | 1 | 1 | 2 | 2 | 3
    // 1,2, 3  | 1 | 1 | 2 | 3 | 4

    public static void main(String[] args) {
        int[] denoms = {1, 2, 5};
                //{1, 2, 3};
        int amount = 5;
                //4;

        int res = coinChange(denoms, amount);

        System.out.println("Result: " + res);
    }
}
