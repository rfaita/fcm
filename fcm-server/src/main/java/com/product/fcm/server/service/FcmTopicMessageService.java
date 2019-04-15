package com.product.fcm.server.service;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.repository.FcmTopicMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FcmTopicMessageService {

    @Autowired
    private FcmTopicMessageRepository fcmTopicMessageRepository;

    public FcmTopicMessage findById(String id) {
        Optional<FcmTopicMessage> ret = fcmTopicMessageRepository.findById(id);
        if (ret.isPresent()) {
            return ret.get();
        }
        return null;
    }

    public List<FcmTopicMessage> findAllByTopic(String Topic) {
        return fcmTopicMessageRepository.findAllByTopic(Topic);
    }

    public FcmTopicMessage save(FcmTopicMessage fcmTopicMessage) {
        return fcmTopicMessageRepository.save(fcmTopicMessage);
    }
}
