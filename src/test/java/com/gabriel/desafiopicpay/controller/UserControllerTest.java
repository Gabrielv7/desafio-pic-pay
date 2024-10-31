package com.gabriel.desafiopicpay.controller;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.mapper.UserMapper;
import com.gabriel.desafiopicpay.domain.enums.UserType;
import com.gabriel.desafiopicpay.service.UserService;
import factory.ScenarioFactory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void Dado_um_novo_usuario_valido_Quando_criar_Entao_deve_retornar_http_status_201() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertToJson(ScenarioFactory.newUserRequest()))
        ).andExpect(status().isCreated());

    }

    @Test
    void Dado_um_novo_usuario_invalido_Quando_criar_Entao_deve_retornar_http_status_400() throws Exception {
        UserRequest userInvalid = new UserRequest("", "", "", "", null, null);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertToJson(userInvalid))
        ).andExpect(status().isBadRequest());

    }

    @Test
    void Dado_um_usuario_que_ja_existe_Quando_criar_Entao_deve_retornar_http_status_400() throws Exception {
        UserRequest userAlreadyExistsInTemplateSql = new UserRequest("Black Panther", "33333333333",
                "blackpanther@email.com", "balck123", UserType.STORE, BigDecimal.valueOf(200));
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertToJson(userAlreadyExistsInTemplateSql))
        ).andExpect(status().isBadRequest());

    }

    @Test
    void Dado_uma_busca_por_uma_lista_de_usuarios_Quando_buscar_Entao_deve_retornar_htpp_status_200() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void Dado_uma_buscar_por_um_usuario_que_exista_Quando_buscar_Entao_deve_retornar_http_status_200() throws Exception {
        Integer userIdExist = 15;
        mockMvc.perform(get("/users/{id}", userIdExist))
                .andExpect(status().isOk());
    }

    @Test
    void Dado_uma_buscar_por_um_usuario_inexistente_Quando_buscar_Entao_deve_retornar_http_status_404() throws Exception {
        Integer idNotExists = -1;
        mockMvc.perform(get("/users/{id}", idNotExists))
                .andExpect(status().isNotFound());
    }

}