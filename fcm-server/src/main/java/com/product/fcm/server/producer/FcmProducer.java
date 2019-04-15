package com.product.fcm.server.producer;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.service.FcmTokenMessageService;
import com.product.fcm.server.service.FcmTopicMessageService;
import com.product.fcm.server.component.FcmMessageValidator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author rfaita
 */
@Service
public class FcmProducer {

    private FcmMessageValidator validator;
    private RabbitTemplate rabbitTemplate;
    private FcmTopicMessageService fcmTopicMessageService;
    private FcmTokenMessageService fcmTokenMessageService;

    @Value("${fcm.send.topic.sendQueue}")
    private String topicQueue;
    @Value("${fcm.send.topic.exchange}")
    private String topicExchange;

    @Value("${fcm.send.token.sendQueue}")
    private String tokenQueue;
    @Value("${fcm.send.token.exchange}")
    private String tokenExchange;

    @Autowired
    public FcmProducer(FcmMessageValidator validator,
                       RabbitTemplate rabbitTemplate,
                       FcmTopicMessageService fcmTopicMessageService,
                       FcmTokenMessageService fcmTokenMessageService) {
        this.validator = validator;
        this.rabbitTemplate = rabbitTemplate;
        this.fcmTopicMessageService = fcmTopicMessageService;
        this.fcmTokenMessageService = fcmTokenMessageService;
    }

    public FcmTokenMessage send(FcmTokenMessage fcmTokenMessage) {

        validator.validate(fcmTokenMessage);

        fcmTokenMessage = fcmTokenMessageService.save(fcmTokenMessage);
        rabbitTemplate.convertAndSend(tokenExchange, tokenQueue, fcmTokenMessage);
        return fcmTokenMessage;
    }

    public FcmTopicMessage send(FcmTopicMessage fcmTopicMessage) {

        validator.validate(fcmTopicMessage);

        fcmTopicMessage = fcmTopicMessageService.save(fcmTopicMessage);
        rabbitTemplate.convertAndSend(topicExchange, topicQueue, fcmTopicMessage);
        return fcmTopicMessage;
    }

}
