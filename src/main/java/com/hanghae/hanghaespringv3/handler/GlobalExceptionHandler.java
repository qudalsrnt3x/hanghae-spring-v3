package com.hanghae.hanghaespringv3.handler;

import com.hanghae.hanghaespringv3.handler.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FoodNameDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleFoodNameDuplicateException(
            FoodNameDuplicateException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceIsNot100UnitException.class)
    public ResponseEntity<ErrorResponse> handlePriceIsNot100UnitException(
            PriceIsNot100UnitException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TotalPriceIsNotValidationException.class)
    public ResponseEntity<ErrorResponse> handleTotalPriceIsNotValidation(
            TotalPriceIsNotValidationException e) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage())
                , HttpStatus.BAD_REQUEST);
    }
}
