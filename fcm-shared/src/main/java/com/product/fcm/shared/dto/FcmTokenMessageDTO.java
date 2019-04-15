package com.product.fcm.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenMessageDTO extends FcmMessageDTO {

    private String token;

    @Builder
    public FcmTokenMessageDTO(String id,
                              String ticket,
                              String body,
                              String title,
                              Map<String, String> data,
                              Date createdAt,
                              Date publishedAt,
                              String organizationId,
                              String errorCode,
                              String errorMessage,
                              String errorStack,
                              String token) {
        super(id, ticket, body, title, data, createdAt,
                publishedAt, organizationId, errorCode, errorMessage, errorStack);
        this.token = token;
    }
}
