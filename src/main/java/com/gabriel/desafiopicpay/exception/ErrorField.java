package com.gabriel.desafiopicpay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorField {
    private String field;
    private String message;
}
