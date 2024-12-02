package com.gabriel.desafiopicpay.strategy.impl;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.repository.UserRepository;
import com.gabriel.desafiopicpay.strategy.NewUserValidationStrategy;
import com.gabriel.desafiopicpay.util.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExistingNameValidationImpl implements NewUserValidationStrategy {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void execute(UserRequest userRequest) {
        if (existsUsername(userRequest)) {
            String message = messageSource.getMessage("user.already.exists", null, LocaleContextHolder.getLocale());
            log.error(Log.LOG_EVENT + Log.LOG_MESSAGE, "[ERROR]", message);
            throw new BusinessException(message);
        }
    }

    private boolean existsUsername(UserRequest userRequest) {
        return userRepository.existsByName(userRequest.name());
    }
}
