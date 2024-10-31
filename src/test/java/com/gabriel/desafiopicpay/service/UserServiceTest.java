package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.exception.NotFoundException;
import com.gabriel.desafiopicpay.mapper.UserMapper;
import com.gabriel.desafiopicpay.repository.UserRepository;
import com.gabriel.desafiopicpay.repository.WalletRepository;
import com.gabriel.desafiopicpay.validator.UserValidator;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

    @Mock
    UserValidator validator;


    @Test
    void Dado_um_user_id_inexistente_Quando_buscar_pelo_id_Entao_deve_retornar_NotFoundException() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Usuário não encontrado.");

        Integer userIdNotExist = -1;
        assertThrows(NotFoundException.class, () -> userService.findById(userIdNotExist));
    }


    @Test
    void Dado_um_user_id_existente_Quando_buscar_pelo_id_Entao_deve_retornar_usuario() {

        when(userRepository.findById(any())).thenReturn(Optional.of(ScenarioFactory.newUserCommonWithBalance100()));

        User user = assertDoesNotThrow(() -> userService.findById(anyInt()));

        assertNotNull(user);
    }

    @Test
    void Quando_buscar_por_uma_lista_de_usuario_Entao_deve_retornar_a_lista_de_usuario() {

        when(userRepository.findAll())
                .thenReturn(List.of(ScenarioFactory.newUserCommonWithBalance100(), ScenarioFactory.newUserStoreWithBalance100()));

        List<User> users = userService.findAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(2, users.size());
        assertFalse(users.isEmpty());
    }


    @Test
    void Quando_criar_um_usuario_valido_Entao_deve_retornar_o_usuario_salvo() {

        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(ScenarioFactory.newUserMapper());
        when(userRepository.save(any(User.class))).thenReturn(ScenarioFactory.newUserCommonWithBalance100());

        User userSaved = userService.save(ScenarioFactory.newUserRequest());

        assertNotNull(userSaved);
        verify(userMapper, times(1)).toEntity(any(UserRequest.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void Quando_criar_um_usuario_que_ja_existe_Entao_deve_lancar_BusinessException() {

        doThrow(BusinessException.class).when(validator).validate(any(UserRequest.class));
        assertThrows(BusinessException.class, () -> userService.save(ScenarioFactory.newUserRequest()));

        verify(userMapper, never()).toEntity(any(UserRequest.class));
        verify(userRepository, never()).save(any(User.class));
    }

}