package com.gabriel.desafiopicpay.client.service;

import com.gabriel.desafiopicpay.client.AuthorizedClient;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class AuthorizedServiceClientTest {

    @InjectMocks
    AuthorizedServiceClient authorizedServiceClient;

    @Mock
    MessageSource messageSource;

    @Mock
    AuthorizedClient authorizedClient;

    @Test
    void Dado_a_validacao_de_autorizacao_Quando_retornar_autorizado_Entao_nao_deve_lancar_exception () {
        when(authorizedClient.getAuthorized()).thenReturn(ScenarioFactory.newAuthorizedResponseWithStatusAuthorized());
        assertDoesNotThrow(() -> authorizedServiceClient.validateAuthorization());
        verify(authorizedClient, times(1)).getAuthorized();
    }

    @Test
    void Dado_a_validacao_de_autorizacao_Quando_nao_retornar_autorizado_Entao_deve_lancar_exception() {
        when(authorizedClient.getAuthorized()).thenReturn(ScenarioFactory.newAuthorizedResponseWithStatusRandom());
        assertThrows(BusinessException.class, () -> authorizedServiceClient.validateAuthorization());
        verify(authorizedClient, times(1)).getAuthorized();
    }

}