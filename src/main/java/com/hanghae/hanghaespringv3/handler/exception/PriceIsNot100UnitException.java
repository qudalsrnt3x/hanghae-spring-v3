package com.hanghae.hanghaespringv3.handler.exception;

public class PriceIsNot100UnitException extends RuntimeException {
    public PriceIsNot100UnitException(String message) {
        super(message);
    }
}
