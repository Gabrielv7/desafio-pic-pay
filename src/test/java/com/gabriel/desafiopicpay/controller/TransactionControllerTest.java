package com.gabriel.desafiopicpay.controller;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.service.TransactionService;
import com.gabriel.desafiopicpay.service.UserService;
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

import java.math.BigDecimal;

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

    private final Integer userTypeCommonId = 10;
    private final Integer userTypeStoreId = 15;

    @Test
    void Dado_uma_transacao_valida_Quando_criar_Entao_deve_retornar_status_http_201() throws Exception {
        TransactionRequest transactionRequest =
                new TransactionRequest(BigDecimal.TEN, userTypeCommonId, userTypeStoreId);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void Dado_uma_transacao_com_pagador_inexistente_Quando_criar_Entao_deve_retornar_status_http_404() throws Exception {
        Integer payerIdNotExist = -1;
        TransactionRequest transactionRequest = new TransactionRequest(BigDecimal.TEN, payerIdNotExist, userTypeStoreId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void Dado_uma_transacao_com_recebedor_inexistente_Quando_criar_Entao_deve_retornar_status_http_404() throws Exception {
        Integer payeeIdNotExist = -1;
        TransactionRequest transactionRequest = new TransactionRequest(BigDecimal.TEN, userTypeCommonId, payeeIdNotExist);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void Dado_uma_transacao_com_pagador_para_o_mesmo_Quando_criar_Entao_deve_retornar_status_http_400() throws Exception {
        TransactionRequest transactionRequest = new TransactionRequest(BigDecimal.TEN, userTypeCommonId, userTypeCommonId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void Dado_uma_transacao_com_pagador_sem_saldo_Quando_criar_Entao_deve_retornar_status_http_400() throws Exception {
        Integer userTypeCommonIdWithoutBalance = 20;
        TransactionRequest transactionRequest = new TransactionRequest(BigDecimal.TEN, userTypeCommonIdWithoutBalance, userTypeCommonId);
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(convertToJson(transactionRequest)))
                .andExpect(status().isBadRequest());
    }

}