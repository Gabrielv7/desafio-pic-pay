package com.gabriel.desafiopicpay.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(@NotNull @Positive Integer value,
                                 @NotNull Integer payer,
                                 @NotNull Integer payee) {

}

