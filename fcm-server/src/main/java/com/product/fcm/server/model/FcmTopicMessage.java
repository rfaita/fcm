package com.product.fcm.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "topicMessage_#{T(com.product.fcm.util.TenantContext).getTenantId()}")
public class FcmTopicMessage extends FcmMessage {

    @NotEmpty(message = "{message.topic.notEmpty}")
    @Indexed
    private String topic;


    @Builder
    public FcmTopicMessage(String id,
                           String ticket,
                           String body,
                           String title,
                           Map<String, String> data,
                           Date createdAt,
                           Date publishedAt,
                           String organizationId,
                           String errorCode, String errorMessage,
                           String errorStack,
                           String topic) {
        super(id, ticket, body, title, data, createdAt,
                publishedAt, organizationId, errorCode, errorMessage, errorStack);
        this.topic = topic;
    }
}
