package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.Wallet;
import com.gabriel.desafiopicpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional
    public void create(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Transactional
    public void transfer(Wallet payerWallet, Wallet payeeWallet, TransactionRequest transactionRequest) {
        payerWallet.subtract(transactionRequest.value());
        payeeWallet.sum(transactionRequest.value());
    }

}
