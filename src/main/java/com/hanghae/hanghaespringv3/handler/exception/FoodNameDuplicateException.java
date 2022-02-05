package com.hanghae.hanghaespringv3.handler.exception;

public class FoodNameDuplicateException extends RuntimeException {
    public FoodNameDuplicateException(String message) {
        super(message);
    }
}
