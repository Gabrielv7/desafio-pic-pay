package com.gabriel.desafiopicpay.domain.validator;


import factory.ScenarioFactory;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {

    @InjectMocks
    private TransactionValidator transactionValidator;

    @Test
    @DisplayName("should fail when user tries to create a transaction for himself")
    void validTransactionCase1() {
        User user = ScenarioFactory.NEW_USER_COMMON;
        TransactionRequest transactionRequest = new TransactionRequest(10, user.getId(), user.getId());
        Wallet wallet = ScenarioFactory.NEW_WALLET_WITH_BALANCE_100;
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> transactionValidator.validTransaction(user, wallet, transactionRequest));
        assertEquals("usuário não pode fazer transação para ele mesmo.", businessException.getMessage());
    }

    @Test
    @DisplayName("should fail when user type shopkeeper tries to create a transaction")
    void validTransactionCase2() {
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> transactionValidator.validTransaction(
                        ScenarioFactory.NEW_USER_STORE,
                        ScenarioFactory.NEW_WALLET_WITH_BALANCE_100,
                        ScenarioFactory.NEW_TRANSACTION_REQUEST));
        assertEquals("Usuário do tipo lojista não está autorizado a realizar transações.", businessException.getMessage());
    }

    @Test
    @DisplayName("should fail when user tries to create a transaction with balance insufficient")
    void validTransactionCase3() {
        BusinessException businessException = assertThrows(BusinessException.class,
                () -> transactionValidator.validTransaction(
                        ScenarioFactory.NEW_USER_COMMON,
                        ScenarioFactory.NEW_WALLET_WITH_BALANCE_0,
                        ScenarioFactory.NEW_TRANSACTION_REQUEST));
        assertEquals("Saldo insuficiente.", businessException.getMessage());
    }

    @Test
    @DisplayName("should not throw any exception when everything is validated")
    void validTransactionCase4() {
        assertDoesNotThrow(() -> transactionValidator.validTransaction(
                        ScenarioFactory.NEW_USER_COMMON,
                        ScenarioFactory.NEW_WALLET_WITH_BALANCE_100,
                        ScenarioFactory.NEW_TRANSACTION_REQUEST));
    }



}