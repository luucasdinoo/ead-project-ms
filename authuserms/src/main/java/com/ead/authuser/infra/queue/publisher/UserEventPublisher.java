package com.ead.authuser.infra.queue.publisher;

import com.ead.authuser.api.model.queue.UserEventDTO;
import com.ead.authuser.domain.model.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.user-event}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDTO dto, ActionType actionType) {
        dto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", dto);
    }
}
