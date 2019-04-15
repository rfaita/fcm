package com.product.fcm.test.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.product.fcm.server.component.FcmMessageValidator;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTokenMessageService;
import com.product.fcm.server.service.FcmTopicMessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;
import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;
import static com.product.fcm.test.helper.GenericBuilder.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "fcm.firebase.dryRun=true"
})
public class FcmServiceTest {

    @SpyBean
    private FcmService service;

    @MockBean
    private FcmMessageValidator validator;

    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @MockBean
    private FcmTopicMessageService fcmTopicMessageService;

    @MockBean
    private FcmTokenMessageService fcmTokenMessageService;


    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        given(fcmTopicMessageService.save(any(FcmTopicMessage.class))).willAnswer(createDefaultAnswer());
        given(fcmTokenMessageService.save(any(FcmTokenMessage.class))).willAnswer(createDefaultAnswer());
        given(firebaseMessaging.subscribeToTopic(any(List.class), any(String.class))).willAnswer(createTopicManagementResponseAnswer());
        given(firebaseMessaging.unsubscribeFromTopic(any(List.class), any(String.class))).willAnswer(createTopicManagementResponseAnswer());
        given(firebaseMessaging.send(any(Message.class), any(Boolean.class))).willAnswer(createStringAnwser("123"));

    }

    @Test
    public void subscribe_with_success() throws Exception {

        service.subscribe("topic1", "token1");

        verify(firebaseMessaging).subscribeToTopic(eq(Arrays.asList("token1")), eq("topic1"));

    }

    @Test
    public void subscribe_with_error() throws Exception {

        exceptionExpect.expect(ValidationException.class);

        service.subscribe("topicError", "token1");

        verify(firebaseMessaging).subscribeToTopic(eq(Arrays.asList("token1")), eq("topicError"));

    }

    @Test
    public void unsubscribe_with_success() throws Exception {

        service.unsubscribe("topic1", "token1");

        verify(firebaseMessaging).unsubscribeFromTopic(eq(Arrays.asList("token1")), eq("topic1"));

    }

    @Test
    public void unsubscribe_with_error() throws Exception {

        exceptionExpect.expect(ValidationException.class);

        service.unsubscribe("topicError", "token1");

        verify(firebaseMessaging).unsubscribeFromTopic(eq(Arrays.asList("token1")), eq("topicError"));

    }

    @Test
    public void send_with_success() throws Exception {

        service.unsubscribe("topic1", "token1");

        verify(firebaseMessaging).unsubscribeFromTopic(eq(Arrays.asList("token1")), eq("topic1"));

    }

    @Test
    public void send_to_token_with_success() throws Exception {

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        FcmTokenMessage ret = service.send(data);

        ArgumentCaptor<FcmTokenMessage> argumentCaptor = ArgumentCaptor.forClass(FcmTokenMessage.class);

        verify(fcmTokenMessageService).save(argumentCaptor.capture());

        Assert.assertEquals("123", argumentCaptor.getValue().getTicket());

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getToken(), ret.getToken());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());

    }

    @Test
    public void send_to_topic_with_success() throws Exception {

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        FcmTopicMessage ret = service.send(data);

        ArgumentCaptor<FcmTopicMessage> argumentCaptor = ArgumentCaptor.forClass(FcmTopicMessage.class);

        verify(fcmTopicMessageService).save(argumentCaptor.capture());

        Assert.assertEquals("123", argumentCaptor.getValue().getTicket());

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getTopic(), ret.getTopic());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());

    }

}
