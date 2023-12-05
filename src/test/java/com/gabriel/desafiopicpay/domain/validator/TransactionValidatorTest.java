package com.gabriel.desafiopicpay.domain.validator;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.User;
import factory.ScenarioFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class TransactionValidatorTest {

    @InjectMocks
    private TransactionValidator transactionValidator;

    @Mock
    MessageSource messageSource;

    @Test
    void Dado_uma_criacao_de_transacao_Quando_usuario_tentar_transferir_para_ele_mesmo_Entao_deve_lancar_BusinessException() {
        User user = ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_100;
        TransactionRequest transactionRequest = new TransactionRequest(10, user.getId(), user.getId());
        assertThrows(BusinessException.class, () -> transactionValidator.validTransaction(user, transactionRequest));

    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_usuario_for_do_tipo_lojista_Entao_deve_lancar_BusinessException() {
        assertThrows(BusinessException.class,
                () -> transactionValidator.validTransaction(
                        ScenarioFactory.NEW_USER_STORE_WITH_BALANCE_100,
                        ScenarioFactory.NEW_TRANSACTION_REQUEST_WITH_VALUE_10));
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_usuario_nao_tiver_saldo_suficiente_Entao_deve_lancar_BusinessException() {
        assertThrows(BusinessException.class,
                () -> transactionValidator.validTransaction(
                        ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_0,
                        ScenarioFactory.NEW_TRANSACTION_REQUEST_WITH_VALUE_10));
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_estiver_tudo_valido_Entao_nao_deve_lancar_exception() {
        assertDoesNotThrow(() -> transactionValidator.validTransaction(
                ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_100,
                ScenarioFactory.NEW_TRANSACTION_REQUEST_WITH_VALUE_10));
    }

}