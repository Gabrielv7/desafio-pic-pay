package com.gabriel.desafiopicpay.domain.validator;

import com.gabriel.desafiopicpay.domain.dto.request.TransactionRequest;
import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.model.User;
import com.gabriel.desafiopicpay.domain.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    public void validTransaction(User payer, Wallet payerWallet, TransactionRequest transactionRequest) {
        if (transactionRequest.payee().equals(payer.getId())) {
            throw new BusinessException("usuário não pode fazer transação para ele mesmo.");
        }
        if (!payer.userIsTypeCommon()) {
            throw new BusinessException("Usuário do tipo lojista não está autorizado a realizar transações.");
        }
        if (!payerWallet.balanceIsBiggerThanZero(transactionRequest.value())) {
            throw new BusinessException("Saldo insuficiente.");
        }
    }

}
