package com.product.fcm.test.helper;

import com.google.firebase.messaging.TopicManagementResponse;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GenericBuilder {

    public static Answer<?> createDefaultAnswer() {
        return (InvocationOnMock invocation) -> {
            return invocation.getArguments()[0];
        };
    }

    public static Answer<TopicManagementResponse> createTopicManagementResponseAnswer() {

        return (InvocationOnMock invocation) -> {

            TopicManagementResponse ret = mock(TopicManagementResponse.class);

            String topic = (String) invocation.getArguments()[1];

            if (topic.equals("topicError")) {

                TopicManagementResponse.Error error = mock(TopicManagementResponse.Error.class);

                given(ret.getErrors()).willReturn(Arrays.asList(error));
            }

            return ret;
        };
    }

    public static Answer<String> createStringAnwser(final String data) {
        return (InvocationOnMock invocation) -> {
            return data;
        };
    }
}
