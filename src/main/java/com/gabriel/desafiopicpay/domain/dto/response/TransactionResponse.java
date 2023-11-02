package com.gabriel.desafiopicpay.domain.dto.response;

import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;

import java.util.UUID;

public record TransactionResponse(UUID id,
                                  String payer,
                                  String payee,
                                  Integer value,
                                  StatusTransaction statusTransaction) {

}
