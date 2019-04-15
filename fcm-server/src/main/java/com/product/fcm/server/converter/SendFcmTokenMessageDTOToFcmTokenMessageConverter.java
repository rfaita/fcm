package com.product.fcm.server.converter;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.shared.dto.SendFcmTokenMessageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SendFcmTokenMessageDTOToFcmTokenMessageConverter
        implements Converter<SendFcmTokenMessageDTO, FcmTokenMessage> {


    @Override
    public FcmTokenMessage convert(SendFcmTokenMessageDTO source) {
        return FcmTokenMessage.builder()
                .token(source.getToken())
                .body(source.getBody())
                .title(source.getTitle())
                .data(source.getData())
                .build();
    }

}
