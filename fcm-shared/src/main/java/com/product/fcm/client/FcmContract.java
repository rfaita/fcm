package com.product.fcm.client;

import com.product.fcm.shared.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface FcmContract {

    @DeleteMapping(value = "/{organizationId}/topic/{topic}/{token}")
    void unsubscribe(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("topic") String topic,
            @PathVariable("token") String token);

    @PutMapping(value = "/{organizationId}/topic/{topic}/{token}")
    void subscribe(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("topic") String topic,
            @PathVariable("token") String token);

    @PostMapping(value = "/{organizationId}/topic/send")
    Envelope<FcmTopicMessageDTO> send(
            @PathVariable("organizationId") String organizationId,
            @RequestBody SendFcmTopicMessageDTO fcmTopicMessage);

    @PostMapping(value = "/{organizationId}/token/send")
    Envelope<FcmTokenMessageDTO> send(
            @PathVariable("organizationId") String organizationId,
            @RequestBody SendFcmTokenMessageDTO fcmTokenMessage);

    @GetMapping(value = "/{organizationId}/topic/{id}")
    Envelope<FcmTopicMessageDTO> findByTopicId(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("id") String id);

    @GetMapping(value = "/{organizationId}/token/{id}")
    Envelope<FcmTokenMessageDTO> findByTokenId(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("id") String id);

    @GetMapping(value = "/{organizationId}/token/all/{token}")
    Envelope<List<FcmTokenMessageDTO>> findAllByToken(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("token") String token);

    @GetMapping(value = "/{organizationId}/topic/all/{topic}")
    Envelope<List<FcmTopicMessageDTO>> findAllByTopic(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("topic") String topic);
}
