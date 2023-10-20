package com.gabriel.desafiopicpay.domain.dto.response;

import java.util.UUID;

public record WalletResponse(UUID id,
                             Integer balance) {
}
