package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.api.client.service.AuthorizedServiceClient;
import com.gabriel.desafiopicpay.domain.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.domain.exception.NotFoundException;
import com.gabriel.desafiopicpay.domain.validator.TransactionValidator;
import com.gabriel.desafiopicpay.messaging.dto.TransactionDTO;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.messaging.dto.TransactionSuccessDTO;
import com.gabriel.desafiopicpay.messaging.publisher.TransactionFailExchangeSender;
import com.gabriel.desafiopicpay.domain.repository.TransactionRepository;
import com.gabriel.desafiopicpay.messaging.publisher.TransactionSuccessExchangeSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final AuthorizedServiceClient authorizedServiceClient;
    private final TransactionFailExchangeSender transactionFailExchangeSender;
    private final TransactionSuccessExchangeSender transactionSuccessExchangeSender;
    private final TransactionValidator validator;
    private final TransactionAssembler assembler;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        User payer = userService.findById(transactionRequest.payer());
        User payee = userService.findById(transactionRequest.payee());
        validator.validTransaction(payer, payer.getWallet(), transactionRequest);
        authorizedServiceClient.validateAuthorized();
        Transaction transaction = assembler.buildCreatedTransaction(payer, payee, transactionRequest.value());
        transaction = saveTransaction(transaction);
        try {
            transfer(payer.getWallet(), payee.getWallet(), transactionRequest);
        } catch (Exception ex) {
            TransactionDTO transactionDTO = assembler.buildTransactionDTO(transaction, payer, payee);
            transactionFailExchangeSender.sendToExchangeTransactionFail(transactionDTO);
            return new TransactionResponse("transaction fail.");
        }
        TransactionDTO transactionDTO = assembler.buildTransactionDTO(transaction, payer, payee);
        transactionSuccessExchangeSender.sendExchangeTransactionSuccess(transactionDTO);
        return new TransactionResponse("transaction success.");
    }

    private Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction findById(UUID transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found."));
    }

    @Transactional
    public void reversedTransaction(TransactionDTO transactionDTO) {
        User userPayer = userService.findById(transactionDTO.payer());
        User userPayee = userService.findById(transactionDTO.payee());
        Transaction transaction = findById(transactionDTO.transactionId());
        revert(userPayer.getWallet(), userPayee.getWallet(), transactionDTO.value());
        transaction.updateStatusFail();
    }

    private void revert(Wallet payerWallet, Wallet payeeWallet, Integer value) {
        payeeWallet.subtract(value);
        payerWallet.receiveTransfer(value);
    }

    private void transfer(Wallet payerWallet, Wallet payeeWallet, TransactionRequest transactionRequest) {
        payerWallet.subtract(transactionRequest.value());
        payeeWallet.receiveTransfer(transactionRequest.value());
    }

    @Transactional
    public void updateStatusSuccess(TransactionSuccessDTO transactionSuccessDTO) {
        Transaction transaction = findById(transactionSuccessDTO.transactionId());
        transaction.updateStatusSuccess();
    }

}
