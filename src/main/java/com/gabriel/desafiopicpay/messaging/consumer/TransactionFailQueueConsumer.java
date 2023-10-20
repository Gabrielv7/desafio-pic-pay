package com.gabriel.desafiopicpay.messaging.consumer;

import com.gabriel.desafiopicpay.messaging.dto.TransactionFailDTO;
import com.gabriel.desafiopicpay.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class TransactionFailQueueConsumer {

    private final TransactionService transactionService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${challenge.broker.queue.transactionFail}",
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = "${challenge.broker.exchange.transactionFail}",
                    type = ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions = "true"
            )
    ))
    public void receiveNotificationCommandMessage(@Payload TransactionFailDTO transactionFailDTO) {
       transactionService.reversedTransaction(transactionFailDTO);
    }

}
