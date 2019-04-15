package com.product.fcm.test.converter;

import com.product.fcm.server.converter.SendFcmTokenMessageDTOToFcmTokenMessageConverter;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.shared.dto.SendFcmTokenMessageDTO;
import org.junit.Assert;
import org.junit.Test;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicSendFcmTokenMessageDTO;

public class SendFcmTokenMessageDTOToFcmTokenMessageConverterTest {

    @Test
    public void convert_send_fcm_topic_message_dto_to_fcm_topic_message_with_success() {

        SendFcmTokenMessageDTO input = createBasicSendFcmTokenMessageDTO("123");

        SendFcmTokenMessageDTOToFcmTokenMessageConverter instance = new SendFcmTokenMessageDTOToFcmTokenMessageConverter();

        FcmTokenMessage ret = instance.convert(input);

        Assert.assertEquals(input.getBody(), ret.getBody());
        Assert.assertEquals(input.getTitle(), ret.getTitle());
        Assert.assertEquals(input.getToken(), ret.getToken());
        Assert.assertEquals(input.getData(), ret.getData());

    }

}
