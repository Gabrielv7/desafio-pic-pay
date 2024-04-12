package com.gabriel.desafiopicpay.api.controller;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.service.TransactionService;
import com.gabriel.desafiopicpay.domain.service.UserService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utilsTest.JsonUtils.convertToJson;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@SqlGroup({
        @Sql(value = "classpath:user-and-wallet-reset-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:user-and-wallet-insert-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    private final UUID userTypeCommonId = UUID.fromString("1f1c42f3-74ef-4a18-84a3-f681cf56d226");
    private final UUID userTypeStoreId = UUID.fromString("4cf6933b-b11c-4e0c-b775-89fd24f1ec09");
    private final UUID userTypeCommonIdWithoutBalance = UUID.fromString("5a0e940a-4915-4460-8512-8efa8f166a7d");

    @Test
    void Dado_uma_transacao_valida_Quando_criar_Entao_deve_retornar_status_http_201() throws Exception {
        TransactionRequest transactionRequest =
                new TransactionRequest(50, userTypeCommonId, userTypeStoreId);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void Dado_uma_transacao_com_pagador_inexistente_Quando_criar_Entao_deve_retornar_status_http_404() throws Exception {
        UUID payerIdNotExist = UUID.randomUUID();
        TransactionRequest transactionRequest = new TransactionRequest(50, payerIdNotExist, userTypeStoreId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void Dado_uma_transacao_com_recebedor_inexistente_Quando_criar_Entao_deve_retornar_status_http_404() throws Exception {
        UUID payeeIdNotExist = UUID.randomUUID();
        TransactionRequest transactionRequest = new TransactionRequest(50, userTypeCommonId, payeeIdNotExist);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void Dado_uma_transacao_com_pagador_para_o_mesmo_Quando_criar_Entao_deve_retornar_status_http_400() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(50, userTypeCommonId, userTypeCommonId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void Dado_uma_transacao_com_pagador_sem_saldo_Quando_criar_Entao_deve_retornar_status_http_400() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(50, userTypeCommonIdWithoutBalance, userTypeCommonId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isBadRequest());
    }

}