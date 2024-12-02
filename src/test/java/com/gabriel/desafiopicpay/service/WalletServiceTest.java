package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.Wallet;
import com.gabriel.desafiopicpay.repository.WalletRepository;
import factory.ScenarioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

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

        Wallet wallet = ScenarioFactory.newWalletWithBalance100();
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        walletService.create(BigDecimal.valueOf(100), ScenarioFactory.newUserStoreWithoutWallet());

        verify(walletRepository, times(1))
                .save(any(Wallet.class));
    }

    @Test
    void Dado_uma_wallet_Quando_transferir_Entao_deve_transferir_os_valores_entre_as_carteiras() {

      Wallet payerWallet = ScenarioFactory.newWalletWithBalance100();
      Wallet payeeWallet = ScenarioFactory.newWalletWithBalance0();
      TransactionRequest transactionRequest = ScenarioFactory.newTransactionRequestWithValue10();

      walletService.transfer(payerWallet, payeeWallet, transactionRequest);

      Assertions.assertEquals(BigDecimal.valueOf(90), payerWallet.getBalance());
      Assertions.assertEquals(BigDecimal.valueOf(10), payeeWallet.getBalance());
    }

}