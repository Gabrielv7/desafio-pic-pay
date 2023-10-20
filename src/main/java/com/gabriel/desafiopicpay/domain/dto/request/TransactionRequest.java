package com.gabriel.desafiopicpay.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record TransactionRequest(@NotNull @Positive Integer value,
                                 @NotNull UUID payer,
                                 @NotNull UUID payee) {

}

