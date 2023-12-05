package com.gabriel.desafiopicpay.api.client.service;

import com.gabriel.desafiopicpay.api.client.dto.AuthorizedResponse;
import com.gabriel.desafiopicpay.api.client.AuthorizedClient;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizedServiceClient {

    private static final String STATUS_AUTHORIZED = "Authorized";

    private final AuthorizedClient authorizedClient;
    private final MessageSource messageSource;

    public void validateAuthorization() {
       AuthorizedResponse request = authorizedClient.getAuthorized();
       if (!STATUS_AUTHORIZED.equalsIgnoreCase(request.message())) {
           throw new BusinessException(messageSource.getMessage("transaction.not.authorized", null, LocaleContextHolder.getLocale()));
       }
    }

}
