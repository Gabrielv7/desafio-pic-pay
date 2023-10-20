package com.gabriel.desafiopicpay.domain.dto.request;

import com.gabriel.desafiopicpay.domain.model.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UserRequest(@NotBlank String name,
                          @NotBlank String document,
                          @NotBlank String email,
                          @NotBlank String password,
                          @NotNull UserType userType,
                          @PositiveOrZero @NotNull Integer balance) {

}
