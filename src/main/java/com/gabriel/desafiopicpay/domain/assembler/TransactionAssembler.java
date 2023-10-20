package com.gabriel.desafiopicpay.domain.assembler;

import com.gabriel.desafiopicpay.domain.model.Transaction;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.enums.StatusTransaction;
import com.gabriel.desafiopicpay.messaging.dto.TransactionFailDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionAssembler {

    public Transaction buildTransactionSuccess(User payer, User payee, Integer value) {
        return Transaction.builder()
                .payer(payer.getId())
                .payee(payee.getId())
                .user(payer)
                .value(value)
                .status(StatusTransaction.SUCCESS)
                .flagEstorno(false)
                .build();
    }

    public TransactionFailDTO buildTransactionFailDTO(Integer value, User payer, User payee,
                                                       LocalDateTime creationDateTime) {
        return TransactionFailDTO.builder()
                .payer(payer.getId())
                .payee(payee.getId())
                .value(value)
                .creationDateTime(creationDateTime)
                .build();
    }

}
