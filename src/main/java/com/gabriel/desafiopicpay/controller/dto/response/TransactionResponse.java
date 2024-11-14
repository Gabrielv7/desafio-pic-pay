package com.gabriel.desafiopicpay.controller.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponse(UUID id,
                                  String payer,
                                  String payee,
                                  BigDecimal value) {

}
