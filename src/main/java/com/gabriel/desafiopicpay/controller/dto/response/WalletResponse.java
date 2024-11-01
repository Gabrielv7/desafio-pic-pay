package com.gabriel.desafiopicpay.controller.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(UUID id,
                             BigDecimal balance) {
}
