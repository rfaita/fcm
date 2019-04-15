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

    public FcmTopicMessage findByOrganizationIdAndId(String organizationId,String id) {
        return fcmTopicMessageRepository.findByOrganizationIdAndId(organizationId, id);
    }

    public List<FcmTopicMessage> findAllByOrganizationIdAndTopic(String organizationId, String topic) {
        return fcmTopicMessageRepository.findAllByOrganizationIdAndTopic(organizationId, topic);
    }

    public FcmTopicMessage save(FcmTopicMessage fcmTopicMessage) {
        return fcmTopicMessageRepository.save(fcmTopicMessage);
    }
}
