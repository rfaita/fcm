package com.product.fcm.test.helper;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.shared.dto.SendFcmTokenMessageDTO;

public class FcmTokenMessageBuilder {

    public static SendFcmTokenMessageDTO createBasicSendFcmTokenMessageDTO(String token) {

        SendFcmTokenMessageDTO ret = new SendFcmTokenMessageDTO();
        ret.setToken(token);
        ret.setBody("olá caras body");
        ret.setTitle("olá caras");
        return ret;

    }

    public static FcmTokenMessage createBasicFcmTokenMessage(String token) {

        return createBasicFcmTokenMessage(null, token);
    }

    public static FcmTokenMessage createBasicFcmTokenMessage(String id, String token) {

        FcmTokenMessage ret = new FcmTokenMessage();
        ret.setToken(token);
        ret.setBody("olá cara body");
        ret.setTitle("olá cara");
        ret.setTicket("321321321");
        ret.setOrganizationId("123");
        ret.setId(id);


        return ret;

    }

    public static FcmTokenMessage createFailFcmTokenMessageWithoutBody() {

        FcmTokenMessage ret = createBasicFcmTokenMessage(null, "t1");

        ret.setBody(null);

        return ret;
    }

    public static FcmTokenMessage createFailFcmTokenMessageWithoutTitle() {

        FcmTokenMessage ret = createBasicFcmTokenMessage(null, "t1");

        ret.setTitle(null);

        return ret;
    }

    public static FcmTokenMessage createFailFcmTokenMessageWithoutToken() {

        FcmTokenMessage ret = createBasicFcmTokenMessage(null, "t1");

        ret.setToken(null);

        return ret;
    }

    public static FcmTokenMessage createFailFcmTokenMessageWithoutOrganization() {

        FcmTokenMessage ret = createBasicFcmTokenMessage(null, "t1");

        ret.setOrganizationId(null);

        return ret;
    }


}
