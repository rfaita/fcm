package com.product.fcm.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendFcmTokenMessageDTO implements Serializable {

    private String token;
    private String title;
    private String body;
    private Map<String, String> data = new HashMap<>();

}
