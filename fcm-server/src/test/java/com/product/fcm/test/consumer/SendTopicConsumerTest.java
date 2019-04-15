package com.product.fcm.test.consumer;

import com.product.fcm.server.consumer.SendTopicConsumer;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTopicMessageService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class SendTopicConsumerTest {

    @SpyBean
    private SendTopicConsumer sendTopicConsumer;

    @MockBean
    private FcmService fcmService;

    @MockBean
    private FcmTopicMessageService service;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Test
    public void send_with_success() throws Exception {

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        sendTopicConsumer.send(data);

        ArgumentCaptor<FcmTopicMessage> argument = ArgumentCaptor.forClass(FcmTopicMessage.class);
        verify(fcmService).send(argument.capture());

        Assert.assertEquals(data.getTopic(), argument.getValue().getTopic());
        Assert.assertEquals(data.getTitle(), argument.getValue().getTitle());
        Assert.assertEquals(data.getBody(), argument.getValue().getBody());
    }

    @Test
    public void send_with_fail() throws Exception {

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        given(fcmService.send(any(FcmTopicMessage.class))).willThrow(new ValidationException("falhou"));

        exceptionExpect.expect(AmqpRejectAndDontRequeueException.class);

        sendTopicConsumer.send(data);

        ArgumentCaptor<FcmTopicMessage> argument = ArgumentCaptor.forClass(FcmTopicMessage.class);
        verify(fcmService).send(argument.capture());

        Assert.assertEquals(data.getTopic(), argument.getValue().getTopic());
        Assert.assertEquals(data.getTitle(), argument.getValue().getTitle());
        Assert.assertEquals(data.getBody(), argument.getValue().getBody());

        verify(service).save(any(FcmTopicMessage.class));
    }

}