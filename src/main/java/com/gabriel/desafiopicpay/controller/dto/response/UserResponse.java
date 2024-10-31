package com.gabriel.desafiopicpay.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabriel.desafiopicpay.domain.enums.UserType;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record UserResponse(String id,
                           String name,
                           String document,
                           String email,
                           UserType userType,
                           WalletResponse wallet) {

}
