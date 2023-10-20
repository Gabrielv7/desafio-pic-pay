package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public Wallet create(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet findById(UUID walletId) {
       return walletRepository.findById(walletId)
                .orElseThrow(() -> new BusinessException(String.format("wallet %s not found.", walletId)));
    }

}
