package com.gabriel.desafiopicpay.client.service;

import com.gabriel.desafiopicpay.client.AuthorizedClient;
import com.gabriel.desafiopicpay.client.dto.AuthorizedResponse;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.exception.ServiceUnavailableException;
import com.gabriel.desafiopicpay.util.Log;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizedServiceClient {

    private static final String STATUS_AUTHORIZED = "Authorized";

    private final AuthorizedClient authorizedClient;
    private final MessageSource messageSource;

    public void validateAuthorization() {

        AuthorizedResponse request;

        try {
            request = authorizedClient.getAuthorized();
        } catch (FeignException e) {
            String message = messageSource.getMessage("authorized.service.is.out", null, LocaleContextHolder.getLocale());
            log.error(Log.LOG_EVENT + Log.LOG_MESSAGE, "[ERROR]", message);
            throw new ServiceUnavailableException(message);
        }

        if (!STATUS_AUTHORIZED.equalsIgnoreCase(request.message())) {
            throw new BusinessException(messageSource.getMessage("transaction.not.authorized", null, LocaleContextHolder.getLocale()));
        }
    }

}
