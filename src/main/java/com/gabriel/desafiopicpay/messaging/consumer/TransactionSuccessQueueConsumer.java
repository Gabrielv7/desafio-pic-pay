package com.gabriel.desafiopicpay.messaging.consumer;

import com.gabriel.desafiopicpay.domain.service.TransactionService;
import com.gabriel.desafiopicpay.messaging.dto.TransactionSuccessDTO;
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
public class TransactionSuccessQueueConsumer {

    private final TransactionService transactionService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${challenge.broker.queue.transactionSuccess}",
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = "${challenge.broker.exchange.transactionSuccess}",
                    type = ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions = "true"
            )
    ))
    public void receiveNotificationCommandMessage(@Payload TransactionSuccessDTO transactionSuccessDTO) {
       transactionService.updateStatusSuccess(transactionSuccessDTO);
    }

}
