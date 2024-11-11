package com.gabriel.desafiopicpay.validator;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.util.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
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
        String message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        log.error(Log.LOG_EVENT + Log.LOG_MESSAGE, "[ERROR]", message);
        throw new BusinessException(messageSource.getMessage(code, null, LocaleContextHolder.getLocale()));
    }


}
