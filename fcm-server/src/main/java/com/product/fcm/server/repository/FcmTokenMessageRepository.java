package com.product.fcm.server.repository;

import com.product.fcm.server.model.FcmTokenMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FcmTokenMessageRepository extends MongoRepository<FcmTokenMessage, String> {

    FcmTokenMessage findByOrganizationIdAndId(String organizationId, String id);

    List<FcmTokenMessage> findAllByOrganizationIdAndToken(String organizationId, String token);

}
