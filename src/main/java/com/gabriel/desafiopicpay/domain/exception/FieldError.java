package com.gabriel.desafiopicpay.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldError {
    private String field;
    private String message;
}
