package com.hanghae.hanghaespringv3.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@AllArgsConstructor
@Getter @Setter
public class ErrorResponse {

    private int status;
    private String message;
}
