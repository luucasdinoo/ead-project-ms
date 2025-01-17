package com.ead.course.infra.queue.consumers;

import com.ead.course.api.model.UserEventDTO;
import com.ead.course.domain.model.enums.ActionType;
import com.ead.course.domain.service.interfaces.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.user-event-queue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.user-event-exchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenUserEvent(@Payload UserEventDTO dto){
        var user = dto.convertToUserModel();

        switch (ActionType.valueOf(dto.getActionType())){
            case CREATE:
            case UPDATE:
                userService.save(user);
                break;
            case DELETE:
                userService.delete(dto.getUserId());
                break;
        }
    }
}
