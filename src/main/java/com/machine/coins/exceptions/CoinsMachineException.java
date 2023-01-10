package com.machine.coins.exceptions;

public class CoinsMachineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static final String defaultMessage = "There is no enough coins to complete this transaction.";

    public CoinsMachineException() {
        super(defaultMessage);
    }
    public CoinsMachineException(String msg) {
        super(msg);
    }

}
