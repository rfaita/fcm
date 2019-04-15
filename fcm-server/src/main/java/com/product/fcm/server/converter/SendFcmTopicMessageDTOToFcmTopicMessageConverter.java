package com.product.fcm.server.converter;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.shared.dto.SendFcmTopicMessageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SendFcmTopicMessageDTOToFcmTopicMessageConverter
        implements Converter<SendFcmTopicMessageDTO, FcmTopicMessage> {


    @Override
    public FcmTopicMessage convert(SendFcmTopicMessageDTO source) {
        return FcmTopicMessage.builder()
                .topic(source.getTopic())
                .body(source.getBody())
                .title(source.getTitle())
                .data(source.getData())
                .build();
    }

}
