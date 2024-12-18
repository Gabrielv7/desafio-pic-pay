package com.gabriel.desafiopicpay.controller;

import com.gabriel.desafiopicpay.controller.dto.request.UserRequest;
import com.gabriel.desafiopicpay.controller.dto.response.UserResponse;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.mapper.UserMapper;
import com.gabriel.desafiopicpay.service.UserService;
import com.gabriel.desafiopicpay.util.Log;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @Operation(summary = "Cria um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "usuário já existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD + Log.LOG_ENTITY,
                "[POST]", "Saving user", "createUser", userRequest);

        User userSaved = service.save(userRequest);

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD + Log.LOG_ENTITY_ID,
                Log.LOG_EVENT_INFO, "User saved", "createUser", userSaved.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(userSaved));
    }


    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD, "[GET]", "find all user", "getAll");

        List<UserResponse> userResponseList = service.findAll().stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(userResponseList);
    }


    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "usuário não existe",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable(value = "id") Integer id) {

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD + Log.LOG_ENTITY_ID,
                "[GET]", "find user", "getOne", id);

        User user = service.findById(id);
        return ResponseEntity.ok(mapper.toResponse(user));
    }


}
