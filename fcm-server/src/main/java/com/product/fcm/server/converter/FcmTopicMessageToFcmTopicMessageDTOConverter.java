package com.product.fcm.server.converter;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.shared.dto.FcmTopicMessageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FcmTopicMessageToFcmTopicMessageDTOConverter
        implements Converter<FcmTopicMessage, FcmTopicMessageDTO> {


    @Override
    public FcmTopicMessageDTO convert(FcmTopicMessage source) {
        return FcmTopicMessageDTO.builder()
                .errorCode(source.getErrorCode())
                .errorMessage(source.getErrorMessage())
                .errorStack(source.getErrorStack())
                .id(source.getId())
                .organizationId(source.getOrganizationId())
                .publishedAt(source.getPublishedAt())
                .ticket(source.getTicket())
                .topic(source.getTopic())
                .body(source.getBody())
                .title(source.getTitle())
                .data(source.getData())
                .createdAt(source.getCreatedAt())
                .build();
    }

}
