package com.gabriel.desafiopicpay.api.controller;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RestController
@RequestMapping
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Realiza uma transação",
            description = "Realiza uma transação por ID, payer é o id do usuário que está fazendo a transação, e payee" +
                    "e o usuário que vai receber o valor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação feita com sucesso.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Falha na transação",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionRequest));
    }

}
