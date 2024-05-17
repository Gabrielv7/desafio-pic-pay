package com.gabriel.desafiopicpay.domain.validator;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionValidator {

    private final MessageSource messageSource;

    public void validateTransaction(User user, TransactionRequest transactionRequest) {
        if (user.getId().equals(transactionRequest.payee())) {
            throw new BusinessException(messageSource.getMessage("user.notAuthorized", null, LocaleContextHolder.getLocale()));
        }
        if (user.userIsTypeStore()) {
            throw new BusinessException(messageSource.getMessage("userType.store", null, LocaleContextHolder.getLocale()));
        }
        if (!user.getWallet().balanceIsBiggerThanZero(transactionRequest.value())) {
            throw new BusinessException(messageSource.getMessage("insufficient.funds", null, LocaleContextHolder.getLocale()));
        }
    }

}
