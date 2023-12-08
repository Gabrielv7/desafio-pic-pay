package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.api.client.service.AuthorizedServiceClient;
import com.gabriel.desafiopicpay.domain.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.repository.TransactionRepository;
import com.gabriel.desafiopicpay.domain.validator.TransactionValidator;
import factory.ScenarioFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    UserService userService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AuthorizedServiceClient authorizedServiceClient;

    @Mock
    TransactionValidator validator;

    @Mock
    TransactionAssembler assembler;

    @Mock
    WalletService walletService;

    @Test
    void Dado_uma_transactionRequest_valida_Quando_criar_transacao_Entao_deve_salvar() {

        User payer = ScenarioFactory.newUserCommonWithBalance100();
        User payee = ScenarioFactory.newUserStoreWithBalance0();
        TransactionRequest transactionRequest = ScenarioFactory.newTransactionRequestWithValue10();
        Transaction transactionSuccess = ScenarioFactory.newTransactionSuccess();

        when(userService.findById(transactionRequest.payer())).thenReturn(payer);
        when(userService.findById(transactionRequest.payee())).thenReturn(payee);
        when(assembler.buildCreatedTransaction(any(), any(), any())).thenReturn(ScenarioFactory.newTransactionBuildCreated());
        when(transactionRepository.save(any())).thenReturn(transactionSuccess);
        when(assembler.buildTransactionResponse(any(), any(), any()))
                .thenReturn(ScenarioFactory.newTransactionResponseDynamicParams(transactionSuccess, payer, payee));

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequest));

        verify(userService, times(1)).findById(transactionRequest.payer());
        verify(userService, times(1)).findById(transactionRequest.payee());
        verify(validator, times(1)).validTransaction(any(), any());
        verify(authorizedServiceClient,times(1)).validateAuthorization();
        verify(assembler, times(1)).buildCreatedTransaction(any(), any(), any());
        verify(assembler, times(1)).buildTransactionResponse(any(), any(), any());
        verify(transactionRepository, times(1)).save(any());

    }


    @Test
    void Dado_uma_transactionRequest_invalida_Quando_criar_transacao_Entao_deve_lancar_BusinessException() {

        User payer = ScenarioFactory.newUserCommonWithBalance100();
        User payee = ScenarioFactory.newUserCommonWithBalance0();
        TransactionRequest transactionRequest = ScenarioFactory.newTransactionRequestWithValue10();

        when(userService.findById(transactionRequest.payer())).thenReturn(payer);
        when(userService.findById(transactionRequest.payee())).thenReturn(payee);
        doThrow(BusinessException.class).when(validator).validTransaction(any(), any());

        assertThrows(BusinessException.class, () -> transactionService.createTransaction(transactionRequest));

        verify(userService, times(1)).findById(transactionRequest.payer());
        verify(userService, times(1)).findById(transactionRequest.payee());
        verify(validator, times(1)).validTransaction(any(), any());
        verify(authorizedServiceClient, never()).validateAuthorization();
        verify(walletService, never()).transfer(any(), any(), any());
        verify(assembler, never()).buildCreatedTransaction(any(), any(), any());
        verify(assembler, never()).buildTransactionResponse(any(), any(), any());
        verify(transactionRepository, never()).save(any());

    }


    @Test
    void Dado_uma_transactionRequest_valida_Quando_transacao_nao_for_autorizada_Entao_deve_lancar_BusinessException() {

        User payer = ScenarioFactory.newUserStoreWithBalance100();
        User payee = ScenarioFactory.newUserCommonWithBalance0();
        TransactionRequest transactionRequest = ScenarioFactory.newTransactionRequestWithValue10();

        when(userService.findById(transactionRequest.payer())).thenReturn(payer);
        when(userService.findById(transactionRequest.payee())).thenReturn(payee);
        doThrow(BusinessException.class).when(authorizedServiceClient).validateAuthorization();

        assertThrows(BusinessException.class, () -> transactionService.createTransaction(transactionRequest));

        verify(userService, times(1)).findById(transactionRequest.payer());
        verify(userService, times(1)).findById(transactionRequest.payee());
        verify(validator, times(1)).validTransaction(any(), any());
        verify(authorizedServiceClient, times(1)).validateAuthorization();
        verify(walletService, never()).transfer(any(), any(), any());
        verify(assembler, never()).buildCreatedTransaction(any(), any(), any());
        verify(assembler, never()).buildTransactionResponse(any(), any(), any());
        verify(transactionRepository, never()).save(any());
    }


}