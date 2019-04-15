package com.product.fcm.test.service;

import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.repository.FcmTokenMessageRepository;
import com.product.fcm.server.service.FcmTokenMessageService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;
import static com.product.fcm.test.helper.GenericBuilder.createDefaultAnswer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class FcmTokenMessageServiceTest {

    @SpyBean
    private FcmTokenMessageService service;

    @MockBean
    private FcmTokenMessageRepository repository;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Test
    public void find_by_id_with_success() {


        FcmTokenMessage data = createBasicFcmTokenMessage("1", "123");

        given(repository.findByOrganizationIdAndId("123", "1")).willReturn(data);

        FcmTokenMessage ret = service.findByOrganizationIdAndId("123", "1");

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getToken(), ret.getToken());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());
        Assert.assertEquals(data.getTicket(), ret.getTicket());

    }

    @Test
    public void find_by_id_not_found() {


        given(repository.findByOrganizationIdAndId("123", "2")).willReturn(null);

        FcmTokenMessage ret = service.findByOrganizationIdAndId("123", "2");

        Assert.assertTrue(ret == null);

    }

    @Test
    public void find_all_by_token_with_success() {


        FcmTokenMessage data = createBasicFcmTokenMessage("1", "123");

        given(repository.findAllByOrganizationIdAndToken("123", "123"))
                .willReturn(Arrays.asList(data));

        List<FcmTokenMessage> ret = service.findAllByOrganizationIdAndToken("123", "123");

        Assert.assertEquals(1, ret.size());

        Assert.assertEquals(data.getId(), ret.get(0).getId());
        Assert.assertEquals(data.getToken(), ret.get(0).getToken());
        Assert.assertEquals(data.getBody(), ret.get(0).getBody());
        Assert.assertEquals(data.getTitle(), ret.get(0).getTitle());
        Assert.assertEquals(data.getTicket(), ret.get(0).getTicket());

    }

    @Test
    public void find_all_by_token_not_found() {


        given(repository.findAllByOrganizationIdAndToken("123", "2")).willReturn(Arrays.asList());

        List<FcmTokenMessage> ret = service.findAllByOrganizationIdAndToken("123", "2");

        Assert.assertTrue(ret.isEmpty());

    }

    @Test
    public void save_with_success() {


        given(repository.save(any(FcmTokenMessage.class))).willAnswer(createDefaultAnswer());

        FcmTokenMessage data = createBasicFcmTokenMessage("123");

        FcmTokenMessage ret = service.save(data);

        Assert.assertEquals(data.getId(), ret.getId());
        Assert.assertEquals(data.getToken(), ret.getToken());
        Assert.assertEquals(data.getBody(), ret.getBody());
        Assert.assertEquals(data.getTitle(), ret.getTitle());
        Assert.assertEquals(data.getTicket(), ret.getTicket());
    }


}
