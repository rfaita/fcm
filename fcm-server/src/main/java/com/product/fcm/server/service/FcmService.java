package com.product.fcm.server.service;

import com.google.firebase.messaging.*;
import com.product.fcm.server.component.FcmMessageValidator;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Date;


@Service
@Slf4j
public class FcmService {


    private FcmMessageValidator validator;
    private FirebaseMessaging firebaseMessaging;
    private FcmTopicMessageService fcmTopicMessageService;
    private FcmTokenMessageService fcmTokenMessageService;

    @Value("${fcm.firebase.dryRun}")
    private Boolean dryRun;

    @Autowired
    public FcmService(FcmMessageValidator validator,
                      FirebaseMessaging firebaseMessaging,
                      FcmTopicMessageService fcmTopicMessageService,
                      FcmTokenMessageService fcmTokenMessageService) {
        this.validator = validator;
        this.firebaseMessaging = firebaseMessaging;
        this.fcmTopicMessageService = fcmTopicMessageService;
        this.fcmTokenMessageService = fcmTokenMessageService;
    }


    public void unsubscribe(String topic, String token) {
        try {
            TopicManagementResponse ret = firebaseMessaging.unsubscribeFromTopic(Arrays.asList(token), topic);
            if (!ret.getErrors().isEmpty()) {
                TopicManagementResponse.Error error = ret.getErrors().get(0);
                throw new ValidationException(error.getReason());
            }
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void subscribe(String topic, String token) {
        try {
            TopicManagementResponse ret = firebaseMessaging.subscribeToTopic(Arrays.asList(token), topic);
            if (!ret.getErrors().isEmpty()) {
                TopicManagementResponse.Error error = ret.getErrors().get(0);
                throw new ValidationException(error.getReason());
            }
        } catch (FirebaseMessagingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public FcmTokenMessage send(FcmTokenMessage fcmTokenMessage) throws FirebaseMessagingException {

        validator.validate(fcmTokenMessage);

        Message message = Message.builder()
                .putAllData(fcmTokenMessage.getData())
                .setToken(fcmTokenMessage.getToken())
                .setNotification(new Notification(fcmTokenMessage.getTitle(), fcmTokenMessage.getBody()))
                .build();

        fcmTokenMessage.setTicket(firebaseMessaging.send(message, dryRun));
        fcmTokenMessage.setPublishedAt(new Date());
        return fcmTokenMessageService.save(fcmTokenMessage);

    }

    public FcmTopicMessage send(FcmTopicMessage fcmTopicMessage) throws FirebaseMessagingException {

        validator.validate(fcmTopicMessage);

        Message message = Message.builder()
                .putAllData(fcmTopicMessage.getData())
                .setTopic(fcmTopicMessage.getTopic())
                .setNotification(new Notification(fcmTopicMessage.getTitle(), fcmTopicMessage.getBody()))
                .build();

        fcmTopicMessage.setTicket(firebaseMessaging.send(message, dryRun));
        fcmTopicMessage.setPublishedAt(new Date());
        return fcmTopicMessageService.save(fcmTopicMessage);
    }

}
