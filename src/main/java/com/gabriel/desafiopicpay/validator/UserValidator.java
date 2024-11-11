package com.gabriel.desafiopicpay.validator;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.repository.UserRepository;
import com.gabriel.desafiopicpay.util.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public void validate(UserRequest userRequest) {
        alreadyExistsDocument(userRequest);
        alreadyExistsName(userRequest);
        alreadyExistsEmail(userRequest);
    }

    private void alreadyExistsEmail(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throwBusinessException();
        }
    }

    private void alreadyExistsName(UserRequest userRequest) {
        if (userRepository.existsByName(userRequest.name())) {
            throwBusinessException();
        }
    }

    private void alreadyExistsDocument(UserRequest userRequest) {
        if (userRepository.existsByDocument(userRequest.document())) {
            throwBusinessException();
        }
    }

    private void throwBusinessException() {
        String message = messageSource.getMessage("user.already.exists", null, LocaleContextHolder.getLocale());
        log.error(Log.LOG_EVENT + Log.LOG_MESSAGE, "[ERROR]", message);
        throw new BusinessException(message);
    }

}
