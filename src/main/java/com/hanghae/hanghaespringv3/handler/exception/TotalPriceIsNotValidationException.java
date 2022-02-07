package com.hanghae.hanghaespringv3.handler.exception;

public class TotalPriceIsNotValidationException extends RuntimeException {
    public TotalPriceIsNotValidationException(String message) {
        super(message);
    }
}
