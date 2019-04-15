package com.product.fcm.server.service;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.repository.FcmTokenMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FcmTokenMessageService {

    @Autowired
    private FcmTokenMessageRepository fcmTokenMessageRepository;

    public FcmTokenMessage findByOrganizationIdAndId(String organizationId, String id) {
        return fcmTokenMessageRepository.findByOrganizationIdAndId(organizationId, id);
    }

    public List<FcmTokenMessage> findAllByOrganizationIdAndToken(String organizationId, String token) {
        return fcmTokenMessageRepository.findAllByOrganizationIdAndToken(organizationId, token);
    }

    public FcmTokenMessage save(FcmTokenMessage fcmTokenMessage) {
        return fcmTokenMessageRepository.save(fcmTokenMessage);
    }
}
