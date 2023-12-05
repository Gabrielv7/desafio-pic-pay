package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.domain.repository.WalletRepository;
import factory.ScenarioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;

    @Test
    void Dado_uma_wallet_Quando_criar_Entao_deve_salvar() {

        Wallet wallet = ScenarioFactory.NEW_WALLET_WITH_BALANCE_100;
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.create(wallet);

        verify(walletRepository, times(1))
                .save(any(Wallet.class));
    }

    @Test
    void Dado_uma_wallet_Quando_transferir_Entao_deve_transferir_os_valores_entre_as_carteiras() {

      Wallet payerWallet = ScenarioFactory.NEW_WALLET_WITH_BALANCE_100;
      Wallet payeeWallet = ScenarioFactory.NEW_WALLET_WITH_BALANCE_0;
      TransactionRequest transactionRequest = ScenarioFactory.NEW_TRANSACTION_REQUEST_WITH_VALUE_10;

      walletService.transfer(payerWallet, payeeWallet, transactionRequest);

      Assertions.assertEquals(90, payerWallet.getBalance());
      Assertions.assertEquals(10, payeeWallet.getBalance());
    }

}