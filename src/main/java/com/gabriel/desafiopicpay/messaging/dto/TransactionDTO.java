package com.gabriel.desafiopicpay.messaging.dto;


import lombok.Builder;

import java.util.UUID;

public record TransactionDTO(UUID transactionId,
                             UUID payer,
                             UUID payee,
                             Integer value) {

    @Builder(toBuilder = true)
    public TransactionDTO {}
}
