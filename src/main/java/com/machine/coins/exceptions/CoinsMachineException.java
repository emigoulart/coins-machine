package com.machine.coins.exceptions;

public class CoinsMachineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CoinsMachineException(String msg) {
        super(msg);
    }
}
