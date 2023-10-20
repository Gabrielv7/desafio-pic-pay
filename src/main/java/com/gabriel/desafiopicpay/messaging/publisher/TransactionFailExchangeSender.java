package com.gabriel.desafiopicpay.messaging.publisher;

import com.gabriel.desafiopicpay.messaging.dto.TransactionFailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionFailExchangeSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${challenge.broker.exchange.transactionFail}")
    private String exchangeTransactionFail;

    public void sendToExchangeTransactionFail(TransactionFailDTO transactionFailDTO) {
        rabbitTemplate.convertAndSend(exchangeTransactionFail, "", transactionFailDTO);
    }

}
