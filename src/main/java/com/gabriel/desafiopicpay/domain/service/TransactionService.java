package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.api.client.service.AuthorizedServiceClient;
import com.gabriel.desafiopicpay.domain.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.domain.validator.TransactionValidator;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.repository.TransactionRepository;
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
        validator.validateTransaction(userPayer, transactionRequest);
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
