package com.gabriel.desafiopicpay.controller;

import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.controller.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.service.TransactionService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Realiza uma transação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação feita com sucesso.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TransactionResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Falha na transação",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD + Log.LOG_ENTITY,
                "[POST]", "Creating transaction", "createTransaction", transactionRequest);

        TransactionResponse transactionCreated = transactionService.createTransaction(transactionRequest);

        log.info(Log.LOG_EVENT + Log.LOG_MESSAGE + Log.LOG_METHOD + Log.LOG_ENTITY_ID,
                Log.LOG_EVENT_INFO, "Transaction created", "createTransaction", transactionCreated.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionCreated);
    }

}
