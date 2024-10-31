package com.gabriel.desafiopicpay.validator;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionValidator {

    private final MessageSource messageSource;

    public void validate(User user, TransactionRequest transactionRequest) {
        validateUserIsNotPayee(user, transactionRequest);
        validateUserType(user);
        validateUserIfHasBalance(user, transactionRequest);
    }

    private void validateUserIfHasBalance(User user, TransactionRequest transactionRequest) {
        if (!user.getWallet().balanceIsBiggerThanZero(transactionRequest.value())) {
            throwBusinessException("insufficient.funds");
        }
    }

    private void validateUserType(User user) {
        if (user.userIsTypeStore()) {
           throwBusinessException("userType.store");
        }
    }

    private void validateUserIsNotPayee(User user, TransactionRequest transactionRequest) {
        if (user.getId().equals(transactionRequest.payee())) {
          throwBusinessException("user.notAuthorized");
        }
    }

    private void throwBusinessException(String code) {
        throw new BusinessException(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
    }


}
