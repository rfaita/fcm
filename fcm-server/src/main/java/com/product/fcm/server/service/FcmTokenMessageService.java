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

    public FcmTokenMessage findById(String id) {
        Optional<FcmTokenMessage> ret = fcmTokenMessageRepository.findById(id);
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    public List<FcmTokenMessage> findAllByToken(String token) {
        return fcmTokenMessageRepository.findAllByToken(token);
    }

    public FcmTokenMessage save(FcmTokenMessage fcmTokenMessage) {
        return fcmTokenMessageRepository.save(fcmTokenMessage);
    }
}
