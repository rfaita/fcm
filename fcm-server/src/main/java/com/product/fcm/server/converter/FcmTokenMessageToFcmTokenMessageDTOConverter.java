package com.product.fcm.server.converter;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.shared.dto.FcmTokenMessageDTO;
import com.product.fcm.shared.dto.SendFcmTokenMessageDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenMessageToFcmTokenMessageDTOConverter
        implements Converter<FcmTokenMessage, FcmTokenMessageDTO> {


    @Override
    public FcmTokenMessageDTO convert(FcmTokenMessage source) {
        return FcmTokenMessageDTO.builder()
                .errorCode(source.getErrorCode())
                .errorMessage(source.getErrorMessage())
                .errorStack(source.getErrorStack())
                .id(source.getId())
                .organizationId(source.getOrganizationId())
                .publishedAt(source.getPublishedAt())
                .ticket(source.getTicket())
                .token(source.getToken())
                .body(source.getBody())
                .title(source.getTitle())
                .data(source.getData())
                .createdAt(source.getCreatedAt())
                .build();
    }

}
