package com.gabriel.desafiopicpay.assembler;

import com.gabriel.desafiopicpay.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.controller.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.Transaction;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.domain.enums.StatusTransaction;
import factory.ScenarioFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class TransactionAssemblerTest {

    @InjectMocks
    private TransactionAssembler transactionAssembler;

    private User userPayer;
    private User userPayee;

    @BeforeEach
    void beforeEach() {
         userPayer = ScenarioFactory.newUserCommonWithBalance100();
         userPayee = ScenarioFactory.newUserStoreWithBalance100();
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_for_construir_o_objeto_de_criacao_Entao_retornar_ele() {

        Transaction transaction = transactionAssembler
                .buildCreatedTransaction(userPayer, userPayee, BigDecimal.TEN);

        assertAll(
                ()-> assertNotNull(transaction),
                ()-> assertEquals(userPayer.getId(), transaction.getPayer()),
                ()-> assertEquals(userPayee.getId(), transaction.getPayee()),
                ()-> assertEquals(userPayer, transaction.getUser()),
                ()-> assertEquals(BigDecimal.TEN, transaction.getAmount()),
                ()-> assertEquals(StatusTransaction.SUCCESS, transaction.getStatus()),
                ()-> assertFalse(transaction.getFlagEstorno())
        );
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_for_construir_o_objeto_de_retorno_Entao_retornar_ele() {
        Transaction transaction = ScenarioFactory.newTransactionSuccess();
        TransactionResponse transactionResponse = transactionAssembler.buildTransactionResponse(userPayer, userPayee, transaction);

        assertAll(
                () -> assertNotNull(transaction),
                () -> assertEquals(transaction.getId(), transactionResponse.id()),
                () -> assertEquals(userPayer.getName(), transactionResponse.payer()),
                () -> assertEquals(userPayee.getName(), transactionResponse.payee()),
                () -> assertEquals(transaction.getAmount(), transactionResponse.value()),
                () -> assertEquals(StatusTransaction.SUCCESS, transactionResponse.statusTransaction())
        );
    }

}