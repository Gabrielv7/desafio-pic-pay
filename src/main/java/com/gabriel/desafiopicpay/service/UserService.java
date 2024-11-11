package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.domain.Wallet;
import com.gabriel.desafiopicpay.exception.NotFoundException;
import com.gabriel.desafiopicpay.mapper.UserMapper;
import com.gabriel.desafiopicpay.repository.UserRepository;
import com.gabriel.desafiopicpay.util.Log;
import com.gabriel.desafiopicpay.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final UserMapper mapper;
    private final MessageSource messageSource;
    private final UserValidator validator;

    @Transactional
    public User save(UserRequest userRequest) {
        validator.validate(userRequest);
        User user = mapper.toEntity(userRequest);
        createWalletWithUser(user, userRequest.balance());
        return userRepository.save(user);
    }

    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                            String message = String.format(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()));
                            log.error(Log.LOG_EVENT + Log.LOG_MESSAGE, "[ERROR]", message);
                            return new NotFoundException(message);
                        }
                );
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void createWalletWithUser(User user, BigDecimal balance) {
        Wallet wallet = new Wallet(balance);
        walletService.create(wallet);
        user.setWallet(wallet);
    }

}
