package com.product.fcm.test.service;

import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.repository.FcmTopicMessageRepository;
import com.product.fcm.server.service.FcmTopicMessageService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;
import static com.product.fcm.test.helper.GenericBuilder.createDefaultAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class FcmTopicMessageServiceTest {

    @SpyBean
    private FcmTopicMessageService service;

    @MockBean
    private FcmTopicMessageRepository repository;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Test
    public void find_by_id_with_success() {


        FcmTopicMessage data = createBasicFcmTopicMessage("1", "123");

        given(repository.findById("1")).willReturn(Optional.of(data));

        FcmTopicMessage ret = service.findById("1");

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getTopic(), ret.getTopic());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());
        Assert.assertEquals(data.getTicket(), ret.getTicket());

    }

    @Test
    public void find_by_id_not_found() {


        given(repository.findById("2")).willReturn(Optional.empty());

        FcmTopicMessage ret = service.findById("2");

        Assert.assertTrue(ret == null);

    }

    @Test
    public void find_all_by_topic_with_success() {


        FcmTopicMessage data = createBasicFcmTopicMessage("1", "123");

        given(repository.findAllByTopic("123")).willReturn(Arrays.asList(data));

        List<FcmTopicMessage> ret = service.findAllByTopic("123");

        Assert.assertEquals(1, ret.size());

        Assert.assertEquals(data.getId(), ret.get(0).getId());
        Assert.assertEquals(data.getTopic(), ret.get(0).getTopic());
        Assert.assertEquals(data.getBody(), ret.get(0).getBody());
        Assert.assertEquals(data.getTitle(), ret.get(0).getTitle());
        Assert.assertEquals(data.getTicket(), ret.get(0).getTicket());

    }

    @Test
    public void find_all_by_topic_not_found() {


        given(repository.findAllByTopic("2")).willReturn(Arrays.asList());

        List<FcmTopicMessage> ret = service.findAllByTopic("2");

        Assert.assertTrue(ret.isEmpty());

    }

    @Test
    public void save_with_success() {


        given(repository.save(any(FcmTopicMessage.class))).willAnswer(createDefaultAnswer());

        FcmTopicMessage data = createBasicFcmTopicMessage("123");

        FcmTopicMessage ret = service.save(data);

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getTopic(), ret.getTopic());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());
        Assert.assertEquals(data.getTicket(), ret.getTicket());
    }


}
