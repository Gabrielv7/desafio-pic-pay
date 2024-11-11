package com.gabriel.desafiopicpay.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorFieldDTO {
    private String field;
    private String message;
}
