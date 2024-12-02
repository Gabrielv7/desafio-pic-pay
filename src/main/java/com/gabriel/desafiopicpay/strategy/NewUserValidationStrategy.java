package com.gabriel.desafiopicpay.strategy;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;

public interface NewUserValidationStrategy {

    void execute(UserRequest userRequest);

}
