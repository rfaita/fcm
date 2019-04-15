package com.product.fcm.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FcmMessage implements Serializable {

    @Id
    protected String id;
    @Indexed
    protected String ticket;
    @NotEmpty(message = "{message.body.notEmpty}")
    protected String body;
    @NotEmpty(message = "{message.title.notEmpty}")
    protected String title;
    protected Map<String, String> data = new HashMap<>();
    protected Date createdAt = new Date();
    @Indexed(name = "expirationDate", expireAfterSeconds = 60 * 60 * 24 * 15)//15 dias
    protected Date publishedAt;

    @NotEmpty(message = "{message.organizationId.notEmpty}")
    protected String organizationId;

    protected String errorCode;
    protected String errorMessage;
    protected String errorStack;

}
