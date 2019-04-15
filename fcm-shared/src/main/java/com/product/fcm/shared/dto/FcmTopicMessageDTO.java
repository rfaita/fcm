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
public class FcmTopicMessageDTO extends FcmMessageDTO {

    private String topic;

    @Builder
    public FcmTopicMessageDTO(String id,
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
                              String topic) {
        super(id, ticket, body, title, data, createdAt,
                publishedAt, organizationId, errorCode, errorMessage, errorStack);
        this.topic = topic;
    }
}
