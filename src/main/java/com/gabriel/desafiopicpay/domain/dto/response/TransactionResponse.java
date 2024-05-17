package com.gabriel.desafiopicpay.domain.dto.response;

import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(UUID id,
                                  String payer,
                                  String payee,
                                  BigDecimal value,
                                  StatusTransaction statusTransaction) {

}
