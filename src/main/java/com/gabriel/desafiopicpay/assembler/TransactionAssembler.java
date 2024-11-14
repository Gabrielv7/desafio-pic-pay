package com.gabriel.desafiopicpay.assembler;

import com.gabriel.desafiopicpay.controller.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.Transaction;
import com.gabriel.desafiopicpay.domain.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionAssembler {

    public Transaction buildCreatedTransaction(User payer, User payee, BigDecimal value) {
        return Transaction.builder()
                .payer(payer.getId())
                .payee(payee.getId())
                .user(payer)
                .amount(value)
                .build();
    }

    public TransactionResponse buildTransactionResponse(User payer, User payee, Transaction transaction) {
        return new TransactionResponse(transaction.getId(),
                                       payer.getName(),
                                       payee.getName(),
                                       transaction.getAmount());
    }

}
