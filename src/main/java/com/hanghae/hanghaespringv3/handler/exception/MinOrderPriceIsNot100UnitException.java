package com.hanghae.hanghaespringv3.handler.exception;

public class MinOrderPriceIsNot100UnitException extends RuntimeException {

    public MinOrderPriceIsNot100UnitException(String message) {
        super(message);
    }
}
