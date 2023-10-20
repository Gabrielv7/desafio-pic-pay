package com.gabriel.desafiopicpay.domain.service;

import com.gabriel.desafiopicpay.api.client.service.AuthorizedServiceClient;
import com.gabriel.desafiopicpay.domain.assembler.TransactionAssembler;
import com.gabriel.desafiopicpay.domain.validator.TransactionValidator;
import com.gabriel.desafiopicpay.messaging.dto.TransactionFailDTO;
import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import com.gabriel.desafiopicpay.messaging.publisher.TransactionFailExchangeSender;
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
    private final TransactionFailExchangeSender transactionFailExchangeSender;
    private final TransactionValidator validator;
    private final TransactionAssembler assembler;

    @Transactional
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        User payer = userService.findById(transactionRequest.payer());
        User payee = userService.findById(transactionRequest.payee());
        validator.validTransaction(payer, payer.getWallet(), transactionRequest);
        authorizedServiceClient.validateAuthorized();
        Transaction transaction = null;
        try {
            transfer(payer.getWallet(), payee.getWallet(), transactionRequest);
            transaction = assembler.buildTransactionSuccess(payer, payee, transactionRequest.value());
            saveTransfer(transaction);
            return new TransactionResponse("transaction success.");
        } catch (Exception ex) {
            assert transaction != null;
            TransactionFailDTO transactionFailDTO = assembler.buildTransactionFailDTO(transactionRequest.value(), payer, payee, transaction.getCreationDate());
            transactionFailExchangeSender.sendToExchangeTransactionFail(transactionFailDTO);
            return new TransactionResponse("transaction fail.");
        }
    }

    @Transactional
    private void saveTransfer(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Transactional
    public void reversedTransaction(TransactionFailDTO transactionFailDTO) {
        User userPayer = userService.findById(transactionFailDTO.payer());
        User userPayee = userService.findById(transactionFailDTO.payee());
        revert(userPayer.getWallet(), userPayee.getWallet(), transactionFailDTO.value());
    }

    @Transactional
    private void revert(Wallet payerWallet, Wallet payeeWallet, Integer value) {
        payeeWallet.subtract(value);
        payerWallet.receiveTransfer(value);
    }

    @Transactional
    private void transfer(Wallet payerWallet, Wallet payeeWallet, TransactionRequest transactionRequest) {
        payerWallet.subtract(transactionRequest.value());
        payeeWallet.receiveTransfer(transactionRequest.value());

    }

}
