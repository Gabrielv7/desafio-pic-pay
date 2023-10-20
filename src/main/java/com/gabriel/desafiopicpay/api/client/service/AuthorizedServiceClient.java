package com.gabriel.desafiopicpay.api.client.service;

import com.gabriel.desafiopicpay.api.client.dto.AuthorizedResponse;
import com.gabriel.desafiopicpay.api.client.AuthorizedClient;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizedServiceClient {

    private static final String STATUS = "Authorized";

    private final AuthorizedClient authorizedClient;

    public void validateAuthorized() {
       AuthorizedResponse request = authorizedClient.getAuthorized();
       if (!STATUS.equalsIgnoreCase(request.message())) {
           throw new BusinessException("Transaction not authorized.");
       }
    }

}
