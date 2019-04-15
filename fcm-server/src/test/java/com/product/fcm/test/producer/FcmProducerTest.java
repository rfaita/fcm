package com.product.fcm.test.producer;


import com.product.fcm.server.component.FcmMessageValidator;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.producer.FcmProducer;
import com.product.fcm.server.service.FcmTokenMessageService;
import com.product.fcm.server.service.FcmTopicMessageService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;
import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;
import static com.product.fcm.test.helper.GenericBuilder.createDefaultAnswer;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class FcmProducerTest {

    @SpyBean
    private FcmProducer producer;

    @MockBean
    private FcmMessageValidator validator;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private FcmTopicMessageService fcmTopicMessageService;
    @MockBean
    private FcmTokenMessageService fcmTokenMessageService;

    @Value("${fcm.send.topic.sendQueue}")
    private String topicQueue;
    @Value("${fcm.send.topic.exchange}")
    private String topicExchange;

    @Value("${fcm.send.token.sendQueue}")
    private String tokenQueue;
    @Value("${fcm.send.token.exchange}")
    private String tokenExchange;


    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() {

        given(fcmTokenMessageService.save(any(FcmTokenMessage.class))).willAnswer(createDefaultAnswer());
        given(fcmTopicMessageService.save(any(FcmTopicMessage.class))).willAnswer(createDefaultAnswer());
    }

    @Test
    public void procude_fcm_with_token_message_with_success() {

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        FcmTokenMessage ret = producer.send(data);

        ArgumentCaptor<FcmTokenMessage> argument = ArgumentCaptor.forClass(FcmTokenMessage.class);
        verify(rabbitTemplate).convertAndSend(eq(tokenExchange), eq(tokenQueue), argument.capture());
        assertEquals(data.getTitle(), argument.getValue().getTitle());
        assertEquals(data.getBody(), argument.getValue().getBody());
        assertEquals(data.getToken(), argument.getValue().getToken());

        assertEquals(data.getTitle(), ret.getTitle());
        assertEquals(data.getBody(), ret.getBody());
        assertEquals(data.getToken(), ret.getToken());


    }

    @Test
    public void procude_fcm_with_topic_message_with_success() {

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        FcmTopicMessage ret = producer.send(data);

        ArgumentCaptor<FcmTopicMessage> argument = ArgumentCaptor.forClass(FcmTopicMessage.class);
        verify(rabbitTemplate).convertAndSend(eq(topicExchange), eq(topicQueue), argument.capture());
        assertEquals(data.getTitle(), argument.getValue().getTitle());
        assertEquals(data.getBody(), argument.getValue().getBody());
        assertEquals(data.getTopic(), argument.getValue().getTopic());

        assertEquals(data.getTitle(), ret.getTitle());
        assertEquals(data.getBody(), ret.getBody());
        assertEquals(data.getTopic(), ret.getTopic());


    }

}
