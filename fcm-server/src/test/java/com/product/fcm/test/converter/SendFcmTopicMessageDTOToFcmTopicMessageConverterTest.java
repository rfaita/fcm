package com.product.fcm.test.converter;

import com.product.fcm.server.converter.SendFcmTopicMessageDTOToFcmTopicMessageConverter;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.shared.dto.SendFcmTopicMessageDTO;
import org.junit.Assert;
import org.junit.Test;

import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicSendFcmTopicMessageDTO;

public class SendFcmTopicMessageDTOToFcmTopicMessageConverterTest {

    @Test
    public void convert_send_fcm_topic_message_dto_to_fcm_topic_message_with_success() {

        SendFcmTopicMessageDTO input = createBasicSendFcmTopicMessageDTO("123");

        SendFcmTopicMessageDTOToFcmTopicMessageConverter instance = new SendFcmTopicMessageDTOToFcmTopicMessageConverter();

        FcmTopicMessage ret = instance.convert(input);

        Assert.assertEquals(input.getBody(), ret.getBody());
        Assert.assertEquals(input.getTitle(), ret.getTitle());
        Assert.assertEquals(input.getTopic(), ret.getTopic());
        Assert.assertEquals(input.getData(), ret.getData());

    }

}
