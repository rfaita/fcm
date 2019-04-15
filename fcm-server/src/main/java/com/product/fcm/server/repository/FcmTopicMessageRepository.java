package com.product.fcm.server.repository;

import com.product.fcm.server.model.FcmTopicMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FcmTopicMessageRepository extends MongoRepository<FcmTopicMessage, String> {

    FcmTopicMessage findByOrganizationIdAndId(String organizationId, String id);

    List<FcmTopicMessage> findAllByOrganizationIdAndTopic(String organizationId, String topic);

}
