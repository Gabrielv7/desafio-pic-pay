package com.gabriel.desafiopicpay.domain.assembler;

import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import com.gabriel.desafiopicpay.messaging.dto.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionAssembler {

    public Transaction buildCreatedTransaction(User payer, User payee, Integer value) {
        return Transaction.builder()
                .payer(payer.getId())
                .payee(payee.getId())
                .user(payer)
                .value(value)
                .status(StatusTransaction.CREATED)
                .flagEstorno(false)
                .build();
    }


    public TransactionDTO buildTransactionDTO(Transaction transaction, User payer, User payee) {
        return TransactionDTO.builder()
                .transactionId(transaction.getId())
                .payer(payer.getId())
                .payee(payee.getId())
                .value(transaction.getValue())
                .build();
    }

}
