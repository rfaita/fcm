package com.product.fcm.test.helper;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.shared.dto.SendFcmTopicMessageDTO;

public class FcmTopicMessageBuilder {


    public static SendFcmTopicMessageDTO createBasicSendFcmTopicMessageDTO(String topic) {

        SendFcmTopicMessageDTO ret = new SendFcmTopicMessageDTO();
        ret.setTopic(topic);
        ret.setBody("olá caras body");
        ret.setTitle("olá caras");
        return ret;

    }

    public static FcmTopicMessage createBasicFcmTopicMessage(String topic) {
        return createBasicFcmTopicMessage(null, topic);
    }

    public static FcmTopicMessage createBasicFcmTopicMessage(String id, String topic) {

        FcmTopicMessage ret = new FcmTopicMessage();
        ret.setTopic(topic);
        ret.setBody("olá caras body");
        ret.setTitle("olá caras");
        ret.setTicket("321321321");
        ret.setOrganizationId("123");
        ret.setId(id);

        return ret;

    }

    public static FcmTopicMessage createFailFcmTopicMessageWithoutBody() {

        FcmTopicMessage ret = createBasicFcmTopicMessage(null, "t1");

        ret.setBody(null);

        return ret;
    }

    public static FcmTopicMessage createFailFcmTopicMessageWithoutTitle() {

        FcmTopicMessage ret = createBasicFcmTopicMessage(null, "t1");

        ret.setTitle(null);

        return ret;
    }

    public static FcmTopicMessage createFailFcmTopicMessageWithoutTopic() {

        FcmTopicMessage ret = createBasicFcmTopicMessage(null, "t1");

        ret.setTopic(null);

        return ret;
    }

    public static FcmTopicMessage createFailFcmTopicMessageWithoutOrganization() {

        FcmTopicMessage ret = createBasicFcmTopicMessage(null, "t1");

        ret.setOrganizationId(null);

        return ret;
    }


}
