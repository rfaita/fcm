package com.product.fcm.test.repository;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.repository.FcmTopicMessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;

@RunWith(SpringRunner.class)
@DataMongoTest
public class FcmTopicMessageRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FcmTopicMessageRepository fcmTopicMessageRepository;

    @Before
    public void setUp() {

        mongoTemplate.save(createBasicFcmTopicMessage("123"));
        mongoTemplate.save(createBasicFcmTopicMessage("123"));
    }

    @Test
    public void find_all_by_token_with_success() {

        List<FcmTopicMessage> list = fcmTopicMessageRepository.findAllByOrganizationIdAndTopic("123", "123");

        Assert.assertEquals(2, list.size());

        FcmTopicMessage ret = list.get(0);

        Assert.assertEquals("123", ret.getTopic());
        Assert.assertEquals("olá caras", ret.getTitle());
        Assert.assertEquals("olá caras body", ret.getBody());
        Assert.assertEquals("321321321", ret.getTicket());

        ret = list.get(1);

        Assert.assertEquals("123", ret.getTopic());
        Assert.assertEquals("olá caras", ret.getTitle());
        Assert.assertEquals("olá caras body", ret.getBody());
        Assert.assertEquals("321321321", ret.getTicket());

    }


}
