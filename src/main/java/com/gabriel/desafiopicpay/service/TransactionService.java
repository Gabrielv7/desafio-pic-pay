package com.gabriel.desafiopicpay.service;

import com.gabriel.desafiopicpay.client.service.AuthorizedServiceClient;
import com.gabriel.desafiopicpay.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.validator.TransactionValidator;
import com.gabriel.desafiopicpay.controller.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.controller.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.Transaction;
import com.gabriel.desafiopicpay.domain.User;
import com.gabriel.desafiopicpay.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final AuthorizedServiceClient authorizedServiceClient;
    private final TransactionValidator validator;
    private final TransactionAssembler assembler;
    private final WalletService walletService;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        User userPayer = userService.findById(transactionRequest.payer());
        User userPayee = userService.findById(transactionRequest.payee());
        validator.validate(userPayer, transactionRequest);
        authorizedServiceClient.validateAuthorization();
        walletService.transfer(userPayer.getWallet(), userPayee.getWallet(), transactionRequest);
        Transaction transaction = assembler.buildCreatedTransaction(userPayer, userPayee, transactionRequest.value());
        Transaction transactionSaved = saveTransaction(transaction);
        return assembler.buildTransactionResponse(userPayer, userPayee, transactionSaved);
    }

    private Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
