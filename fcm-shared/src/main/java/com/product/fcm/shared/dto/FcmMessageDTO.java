package com.product.fcm.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FcmMessageDTO implements Serializable {

    private String id;
    private String ticket;
    private String body;
    private String title;
    private Map<String, String> data = new HashMap<>();
    private Date createdAt;
    private Date publishedAt;

    private String organizationId;

    private String errorCode;
    private String errorMessage;
    private String errorStack;


}
