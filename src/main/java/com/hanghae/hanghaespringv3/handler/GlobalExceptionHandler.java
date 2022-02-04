package com.hanghae.hanghaespringv3.handler;

import com.hanghae.hanghaespringv3.handler.exception.DeliveryFeeIsNot500UnitException;
import com.hanghae.hanghaespringv3.handler.exception.ErrorResponse;
import com.hanghae.hanghaespringv3.handler.exception.MinOrderPriceIsNot100UnitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MinOrderPriceIsNot100UnitException.class)
    public ResponseEntity<ErrorResponse> handleMinOrderPriceIsNot100UnitException(
            MinOrderPriceIsNot100UnitException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
        , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeliveryFeeIsNot500UnitException.class)
    public ResponseEntity<ErrorResponse> handleDeliveryFeeIsNot500UnitException(
            DeliveryFeeIsNot500UnitException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST);
    }
}
