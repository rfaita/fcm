package com.product.fcm.server.consumer;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTokenMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SendTokenConsumer {


    private FcmService fcmService;
    private FcmTokenMessageService service;

    @Autowired
    public SendTokenConsumer(FcmService fcmService,
                             FcmTokenMessageService service) {
        this.fcmService = fcmService;
        this.service = service;
    }

    @RabbitListener(queues = "${fcm.send.token.sendQueue}",
            containerFactory = "customRabbitListenerContainerFactory")
    public void send(final FcmTokenMessage fcmTokenMessage) {
        try {

            try {


                log.info("Realizando envio da MSG, para o token '{}'.", fcmTokenMessage.getToken());
                fcmService.send(fcmTokenMessage);
                log.info("Sucesso no envio da MSG, para o token '{}'.", fcmTokenMessage.getToken());

            } catch (FirebaseMessagingException e) {
                fcmTokenMessage.setErrorCode(e.getErrorCode());
                fcmTokenMessage.setErrorMessage(e.getMessage());
                fcmTokenMessage.setErrorStack(ExceptionUtils.getFullStackTrace(e));
                service.save(fcmTokenMessage);
                throw new AmqpRejectAndDontRequeueException(e);
            } catch (Exception e) {
                fcmTokenMessage.setErrorMessage(e.getMessage());
                fcmTokenMessage.setErrorStack(ExceptionUtils.getFullStackTrace(e));
                service.save(fcmTokenMessage);
                throw new AmqpRejectAndDontRequeueException(e);
            }
        } catch (Exception e) {
            log.error("Erro envio da MSG, para o token '{}'.", fcmTokenMessage.getToken(), e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }


}
