package com.gabriel.desafiopicpay.domain.assembler;

import com.gabriel.desafiopicpay.domain.dto.response.TransactionResponse;
import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionAssembler {

    public Transaction buildCreatedTransaction(User payer, User payee, Integer value) {
        return Transaction.builder()
                .payer(payer.getId())
                .payee(payee.getId())
                .user(payer)
                .value(value)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .build();
    }

    public TransactionResponse buildTransactionResponse(User payer, User payee, Transaction transaction) {
        return new TransactionResponse(transaction.getId(),
                                       payer.getName(),
                                       payee.getName(),
                                       transaction.getValue(),
                                       StatusTransaction.SUCCESS);
    }

}
