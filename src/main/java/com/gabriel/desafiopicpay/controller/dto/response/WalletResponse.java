package com.gabriel.desafiopicpay.controller.dto.response;

import java.util.UUID;

public record WalletResponse(UUID id,
                             Integer balance) {
}
