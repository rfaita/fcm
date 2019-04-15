package com.product.fcm.test.consumer;

import com.product.fcm.server.consumer.SendTokenConsumer;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTokenMessageService;
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

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class SendTokenConsumerTest {

    @SpyBean
    private SendTokenConsumer sendTokenConsumer;

    @MockBean
    private FcmService fcmService;

    @MockBean
    private FcmTokenMessageService service;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Test
    public void send_with_success() throws Exception {

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        sendTokenConsumer.send(data);

        ArgumentCaptor<FcmTokenMessage> argument = ArgumentCaptor.forClass(FcmTokenMessage.class);
        verify(fcmService).send(argument.capture());

        Assert.assertEquals(data.getToken(), argument.getValue().getToken());
        Assert.assertEquals(data.getTitle(), argument.getValue().getTitle());
        Assert.assertEquals(data.getBody(), argument.getValue().getBody());
    }

    @Test
    public void send_with_fail() throws Exception {

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        given(fcmService.send(any(FcmTokenMessage.class))).willThrow(new ValidationException("falhou"));

        exceptionExpect.expect(AmqpRejectAndDontRequeueException.class);

        sendTokenConsumer.send(data);

        ArgumentCaptor<FcmTokenMessage> argument = ArgumentCaptor.forClass(FcmTokenMessage.class);
        verify(fcmService).send(argument.capture());

        Assert.assertEquals(data.getToken(), argument.getValue().getToken());
        Assert.assertEquals(data.getTitle(), argument.getValue().getTitle());
        Assert.assertEquals(data.getBody(), argument.getValue().getBody());

        verify(service).save(any(FcmTokenMessage.class));

    }

}