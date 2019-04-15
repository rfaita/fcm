package com.product.fcm.test.repository;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.repository.FcmTokenMessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;

@RunWith(SpringRunner.class)
@DataMongoTest
public class FcmTokenMessageRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FcmTokenMessageRepository fcmTokenMessageRepository;

    @Before
    public void setUp() {

        mongoTemplate.save(createBasicFcmTokenMessage("1", "123"));
        mongoTemplate.save(createBasicFcmTokenMessage("2", "123"));
    }

    @Test
    public void find_all_by_token_with_success() {

        List<FcmTokenMessage> list = fcmTokenMessageRepository.findAllByToken("123");

        Assert.assertEquals(2, list.size());

        FcmTokenMessage ret = list.get(0);

        Assert.assertEquals("123", ret.getToken());
        Assert.assertEquals("olá cara", ret.getTitle());
        Assert.assertEquals("olá cara body", ret.getBody());
        Assert.assertEquals("321321321", ret.getTicket());

        ret = list.get(1);

        Assert.assertEquals("123", ret.getToken());
        Assert.assertEquals("olá cara", ret.getTitle());
        Assert.assertEquals("olá cara body", ret.getBody());
        Assert.assertEquals("321321321", ret.getTicket());

    }


}
