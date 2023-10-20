package com.gabriel.desafiopicpay.messaging.dto;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionFailDTO(UUID payer,
                                 UUID payee,
                                 Integer value,
                                 LocalDateTime creationDateTime) {

    @Builder(toBuilder = true)
    public TransactionFailDTO {}
}
