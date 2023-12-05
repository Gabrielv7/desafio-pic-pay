package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.exception.NotFoundException;
import com.gabriel.desafiopicpay.domain.mapper.UserMapper;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final UserMapper mapper;
    private final MessageSource messageSource;

    @Transactional
    public User save(UserRequest userRequest) {
        validate(userRequest);
        User user = mapper.toEntity(userRequest);
        createWalletWithUser(user, userRequest.balance());
        return userRepository.save(user);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale()), userId)));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void createWalletWithUser(User user, Integer balance) {
        Wallet wallet = new Wallet(balance);
        walletService.create(wallet);
        user.setWallet(wallet);
    }

    private void validate(UserRequest userRequest) {
        if (userRepository.existsByDocument(userRequest.document()) || userRepository.existsByName(userRequest.name())
                || userRepository.existsByEmail(userRequest.email())) {
            throw new BusinessException(messageSource.getMessage("user.already.exists", null, LocaleContextHolder.getLocale()));
        }
    }
}
