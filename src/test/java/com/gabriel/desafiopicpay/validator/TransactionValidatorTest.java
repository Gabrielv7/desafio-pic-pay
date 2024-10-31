package com.gabriel.desafiopicpay.validator;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.exception.BusinessException;
import factory.ScenarioFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;

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
        User user = ScenarioFactory.newUserCommonWithBalance100();
        TransactionRequest transactionRequest = new TransactionRequest(BigDecimal.TEN, user.getId(), user.getId());
        assertThrows(BusinessException.class, () -> transactionValidator.validate(user, transactionRequest));

    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_usuario_for_do_tipo_lojista_Entao_deve_lancar_BusinessException() {
        assertThrows(BusinessException.class,
                () -> transactionValidator.validate(
                        ScenarioFactory.newUserStoreWithBalance100(),
                        ScenarioFactory.newTransactionRequestWithValue10()));
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_usuario_nao_tiver_saldo_suficiente_Entao_deve_lancar_BusinessException() {
        assertThrows(BusinessException.class,
                () -> transactionValidator.validate(
                        ScenarioFactory.newUserCommonWithBalance0(),
                        ScenarioFactory.newTransactionRequestWithValue10()));
    }

    @Test
    void Dado_uma_criacao_de_transacao_Quando_estiver_tudo_valido_Entao_nao_deve_lancar_exception() {
        assertDoesNotThrow(() -> transactionValidator.validate(
                ScenarioFactory.newUserCommonWithBalance100(),
                ScenarioFactory.newTransactionRequestWithValue10()));
    }

}