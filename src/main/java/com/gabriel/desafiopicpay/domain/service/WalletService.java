package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.repository.WalletRepository;
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
