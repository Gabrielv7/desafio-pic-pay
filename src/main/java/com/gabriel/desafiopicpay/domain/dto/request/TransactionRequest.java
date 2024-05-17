package com.gabriel.desafiopicpay.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequest(@NotNull @Positive BigDecimal value,
                                 @NotNull Integer payer,
                                 @NotNull Integer payee) {

}

