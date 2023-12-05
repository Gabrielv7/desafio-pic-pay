package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.domain.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.exception.NotFoundException;
import com.gabriel.desafiopicpay.domain.mapper.UserMapper;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.repository.UserRepository;
import com.gabriel.desafiopicpay.domain.repository.WalletRepository;
import factory.ScenarioFactory;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    MessageSource messageSource;

    @Mock
    UserMapper userMapper;

    @Mock
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;


    @Test
    void Dado_um_user_id_inexistente_Quando_buscar_pelo_id_Entao_deve_retornar_NotFoundException() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Usuário não encontrado.");

        assertThrows(NotFoundException.class, () -> userService.findById(UUID.randomUUID()));
    }


    @Test
    void Dado_um_user_id_existente_Quando_buscar_pelo_id_Entao_deve_retornar_usuario() {

        when(userRepository.findById(any())).thenReturn(Optional.of(ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_100));

        User user = assertDoesNotThrow(() -> userService.findById(UUID.randomUUID()));

        assertNotNull(user);
    }

    @Test
    void Quando_buscar_por_uma_lista_de_usuario_Entao_deve_retornar_a_lista_de_usuario() {

        when(userRepository.findAll())
                .thenReturn(List.of(ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_100, ScenarioFactory.NEW_USER_STORE_WITH_BALANCE_100));

        List<User> users = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(2, users.size());
        assertFalse(users.isEmpty());
    }


    @Test
    void Quando_criar_um_usuario_valido_Entao_deve_retornar_o_usuario_salvo() {

        when(userRepository.existsByDocument(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(ScenarioFactory.NEW_USER_MAPPER);
        when(userRepository.save(any(User.class))).thenReturn(ScenarioFactory.NEW_USER_COMMON_WITH_BALANCE_100);

        User userSaved = userService.save(ScenarioFactory.NEW_USER_REQUEST);

        assertNotNull(userSaved);
        verify(userRepository, times(1)).existsByDocument(anyString());
        verify(userMapper, times(1)).toEntity(any(UserRequest.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void Quando_criar_um_usuario_que_ja_existe_Entao_deve_lancar_BusinessException() {

        when(userRepository.existsByDocument(anyString())).thenReturn(true);

        assertThrows(BusinessException.class,
                () -> userService.save(ScenarioFactory.NEW_USER_REQUEST));

        verify(userRepository, times(1)).existsByDocument(anyString());
        verify(userMapper, never()).toEntity(any(UserRequest.class));
        verify(userRepository, never()).save(any(User.class));
    }

}