package com.gabriel.desafiopicpay.messaging.publisher;

import com.gabriel.desafiopicpay.messaging.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionSuccessExchangeSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${challenge.broker.exchange.transactionSuccess}")
    private String exchangeTransactionSuccess;

    public void sendExchangeTransactionSuccess(TransactionDTO transactionDTO) {
        rabbitTemplate.convertAndSend(exchangeTransactionSuccess, "", transactionDTO);
    }

}
