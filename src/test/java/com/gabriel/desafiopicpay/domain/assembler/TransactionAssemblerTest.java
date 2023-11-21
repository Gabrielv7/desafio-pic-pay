package com.gabriel.desafiopicpay.domain.assembler;

import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import factory.ScenarioFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TransactionAssemblerTest {

    @InjectMocks
    private TransactionAssembler transactionAssembler;

    @Test
    void buildCreatedTransaction() {
        User userPayer = ScenarioFactory.NEW_USER_COMMON;
        User userPayee = ScenarioFactory.NEW_USER_STORE;
        Transaction transaction = transactionAssembler
                .buildCreatedTransaction(userPayer, userPayee, 10);

        assertAll(
                ()-> assertNotNull(transaction),
                ()-> assertEquals(userPayer.getId(), transaction.getPayer()),
                ()-> assertEquals(userPayee.getId(), transaction.getPayee()),
                ()-> assertEquals(userPayer, transaction.getUser()),
                ()-> assertEquals(10, transaction.getValue()),
                ()-> assertEquals(StatusTransaction.SUCCESS, transaction.getStatus()),
                ()-> assertFalse(transaction.getFlagEstorno())
        );
    }

    @Test
    void buildTransactionResponse() {
        User userPayer = ScenarioFactory.NEW_USER_COMMON;
        User userPayee = ScenarioFactory.NEW_USER_STORE;
        Transaction transaction = ScenarioFactory.NEW_TRANSACTION_SUCCESS;
        TransactionResponse transactionResponse = transactionAssembler.buildTransactionResponse(userPayer, userPayee, transaction);

        assertAll(
                () -> assertNotNull(transaction),
                () -> assertEquals(transaction.getId(), transactionResponse.id()),
                () -> assertEquals(userPayer.getName(), transactionResponse.payer()),
                () -> assertEquals(userPayee.getName(), transactionResponse.payee()),
                () -> assertEquals(transaction.getValue(), transactionResponse.value()),
                () -> assertEquals(StatusTransaction.SUCCESS, transactionResponse.statusTransaction())
        );
    }

}