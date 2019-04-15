package com.product.fcm.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.fcm.server.controller.FcmController;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.producer.FcmProducer;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTokenMessageService;
import com.product.fcm.server.service.FcmTopicMessageService;
import com.product.fcm.shared.dto.SendFcmTokenMessageDTO;
import com.product.fcm.shared.dto.SendFcmTopicMessageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicFcmTokenMessage;
import static com.product.fcm.test.helper.FcmTokenMessageBuilder.createBasicSendFcmTokenMessageDTO;
import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicFcmTopicMessage;
import static com.product.fcm.test.helper.FcmTopicMessageBuilder.createBasicSendFcmTopicMessageDTO;
import static com.product.fcm.test.helper.GenericBuilder.createDefaultAnswer;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false, controllers = FcmController.class)
public class FcmControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FcmTopicMessageService fcmTopicMessageService;
    @MockBean
    private FcmTokenMessageService fcmTokenMessageService;
    @MockBean
    private FcmService fcmService;
    @MockBean
    private FcmProducer fcmProducer;


    @Test
    public void unsubscribe_with_success() throws Exception {

        doNothing().when(fcmService).unsubscribe("topic1", "token1");

        mvc.perform(
                delete("/fcm/teste1/topic/topic1/token1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(fcmService, times(1)).unsubscribe("topic1_teste1", "token1");
    }

    @Test
    public void subscribe_with_success() throws Exception {

        doNothing().when(fcmService).subscribe("topic1", "token1");

        mvc.perform(
                put("/fcm/teste1/topic/topic1/token1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(fcmService, times(1)).subscribe("topic1_teste1", "token1");
    }

    @Test
    public void send_message_with_topic_with_success() throws Exception {

        SendFcmTopicMessageDTO input = createBasicSendFcmTopicMessageDTO("123");

        given(fcmProducer.send(any(FcmTopicMessage.class))).willAnswer(createDefaultAnswer());

        String dados = mapper.writeValueAsString(input);

        mvc.perform(
                post("/fcm/teste1/topic/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dados)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.topic", is(input.getTopic().concat("_").concat("teste1"))))
                .andExpect(jsonPath("$.data.body", is(input.getBody())))
                .andExpect(jsonPath("$.data.title", is(input.getTitle())));
    }

    @Test
    public void send_message_with_token_with_success() throws Exception {

        SendFcmTokenMessageDTO input = createBasicSendFcmTokenMessageDTO("123");

        given(fcmProducer.send(any(FcmTokenMessage.class))).willAnswer(createDefaultAnswer());

        String dados = mapper.writeValueAsString(input);

        mvc.perform(
                post("/fcm/teste1/token/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dados)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token", is(input.getToken())))
                .andExpect(jsonPath("$.data.body", is(input.getBody())))
                .andExpect(jsonPath("$.data.title", is(input.getTitle())));
    }

    @Test
    public void find_by_id_topic_message_with_success() throws Exception {

        FcmTopicMessage input = createBasicFcmTopicMessage("1", "123");

        given(fcmTopicMessageService.findByOrganizationIdAndId("teste1","1")).willReturn(input);

        mvc.perform(
                get("/fcm/teste1/topic/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.topic", is(input.getTopic())))
                .andExpect(jsonPath("$.data.body", is(input.getBody())))
                .andExpect(jsonPath("$.data.title", is(input.getTitle())));
    }

    @Test
    public void find_by_id_token_message_with_success() throws Exception {

        FcmTokenMessage input = createBasicFcmTokenMessage("1", "123");

        given(fcmTokenMessageService.findByOrganizationIdAndId("teste1", "1")).willReturn(input);

        mvc.perform(
                get("/fcm/teste1/token/1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.token", is(input.getToken())))
                .andExpect(jsonPath("$.data.body", is(input.getBody())))
                .andExpect(jsonPath("$.data.title", is(input.getTitle())));
    }


    @Test
    public void find_all_by_topic_with_success() throws Exception {

        FcmTopicMessage input = createBasicFcmTopicMessage("1", "t1");
        FcmTopicMessage input2 = createBasicFcmTopicMessage("2", "t1");

        given(fcmTopicMessageService.findAllByOrganizationIdAndTopic("teste1","t1")).willReturn(
                Arrays.asList(
                        input, input2
                )
        );

        mvc.perform(
                get("/fcm/teste1/topic/all/t1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))

                .andExpect(jsonPath("$.data[0].topic", is(input.getTopic())))
                .andExpect(jsonPath("$.data[0].body", is(input.getBody())))
                .andExpect(jsonPath("$.data[0].title", is(input.getTitle())))
                .andExpect(jsonPath("$.data[0].id", is(input.getId())))

                .andExpect(jsonPath("$.data[1].topic", is(input2.getTopic())))
                .andExpect(jsonPath("$.data[1].body", is(input2.getBody())))
                .andExpect(jsonPath("$.data[1].title", is(input2.getTitle())))
                .andExpect(jsonPath("$.data[1].id", is(input2.getId())));
    }

    @Test
    public void find_all_by_token_with_success() throws Exception {

        FcmTokenMessage input = createBasicFcmTokenMessage("1", "t1");
        FcmTokenMessage input2 = createBasicFcmTokenMessage("2", "t1");

        given(fcmTokenMessageService.findAllByOrganizationIdAndToken("teste1","t1")).willReturn(
                Arrays.asList(
                        input, input2
                )
        );

        mvc.perform(
                get("/fcm/teste1/token/all/t1")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))

                .andExpect(jsonPath("$.data[0].token", is(input.getToken())))
                .andExpect(jsonPath("$.data[0].body", is(input.getBody())))
                .andExpect(jsonPath("$.data[0].title", is(input.getTitle())))
                .andExpect(jsonPath("$.data[0].id", is(input.getId())))

                .andExpect(jsonPath("$.data[1].token", is(input2.getToken())))
                .andExpect(jsonPath("$.data[1].body", is(input2.getBody())))
                .andExpect(jsonPath("$.data[1].title", is(input2.getTitle())))
                .andExpect(jsonPath("$.data[1].id", is(input2.getId())));
    }

}
