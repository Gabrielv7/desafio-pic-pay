package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.exception.NotFoundException;
import com.gabriel.desafiopicpay.mapper.UserMapper;
import com.gabriel.desafiopicpay.repository.UserRepository;
import com.gabriel.desafiopicpay.repository.WalletRepository;
import com.gabriel.desafiopicpay.strategy.NewUserValidationStrategy;
import com.gabriel.desafiopicpay.strategy.impl.ExistingNameValidationImpl;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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

    @Mock
    List<NewUserValidationStrategy> validation;


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

        NewUserValidationStrategy validation = mock(ExistingNameValidationImpl.class);

        doThrow(new BusinessException("teste"))
                .when(validation).execute(any(UserRequest.class));

        List<NewUserValidationStrategy> validations = List.of(validation);
        UserService userService = new UserService(userRepository, walletService, userMapper, messageSource, validations);

        // Executando e verificando o comportamento
        assertThrows(BusinessException.class, () -> userService.save(ScenarioFactory.newUserRequest()));

        // Verificando que métodos de mapeamento e salvamento não foram chamados
        verify(userMapper, never()).toEntity(any(UserRequest.class));
        verify(userRepository, never()).save(any(User.class));
    }

}