package com.product.fcm.server.consumer;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTopicMessageService;
import com.product.fcm.util.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SendTopicConsumer {

    private FcmService fcmService;
    private FcmTopicMessageService service;

    @Autowired
    public SendTopicConsumer(FcmService fcmService,
                             FcmTopicMessageService service) {
        this.fcmService = fcmService;
        this.service = service;
    }

    @RabbitListener(queues = "${fcm.send.topic.sendQueue}", 
            containerFactory = "customRabbitListenerContainerFactory")
    public void send(final FcmTopicMessage fcmTopicMessage) {
        try {

            TenantContext.setTenantId(fcmTopicMessage.getOrganizationId());

            try {
                log.info("Realizando envio da MSG, para o topic '{}'.", fcmTopicMessage.getTopic());
                fcmService.send(fcmTopicMessage);
                log.info("Sucesso envio da MSG, para o topic '{}'.", fcmTopicMessage.getTopic());

            } catch (FirebaseMessagingException e) {
                fcmTopicMessage.setErrorCode(e.getErrorCode());
                fcmTopicMessage.setErrorMessage(e.getMessage());
                fcmTopicMessage.setErrorStack(ExceptionUtils.getFullStackTrace(e));
                service.save(fcmTopicMessage);
                throw new AmqpRejectAndDontRequeueException(e);
            } catch (Exception e) {
                fcmTopicMessage.setErrorMessage(e.getMessage());
                fcmTopicMessage.setErrorStack(ExceptionUtils.getFullStackTrace(e));
                service.save(fcmTopicMessage);
                throw new AmqpRejectAndDontRequeueException(e);
            }
        } catch (Exception e) {
            log.error("Erro envio da MSG, para o topic '{}'.", fcmTopicMessage.getTopic(), e);
            throw new AmqpRejectAndDontRequeueException(e);
        } finally {
            TenantContext.clear();
        }
    }

}
