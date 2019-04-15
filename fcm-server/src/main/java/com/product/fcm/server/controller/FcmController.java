package com.product.fcm.server.controller;

import com.product.fcm.client.FcmContract;
import com.product.fcm.server.model.FcmTokenMessage;
import com.product.fcm.server.model.FcmTopicMessage;
import com.product.fcm.server.producer.FcmProducer;
import com.product.fcm.server.service.FcmService;
import com.product.fcm.server.service.FcmTokenMessageService;
import com.product.fcm.server.service.FcmTopicMessageService;
import com.product.fcm.shared.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/fcm")
@Slf4j
public class FcmController implements FcmContract {


    private FcmTopicMessageService fcmTopicMessageService;
    private FcmTokenMessageService fcmTokenMessageService;
    private FcmService fcmService;
    private FcmProducer fcmProducer;

    private ConversionService conversionService;


    @Autowired
    public FcmController(FcmTopicMessageService fcmTopicMessageService,
                         FcmTokenMessageService fcmTokenMessageService,
                         FcmService fcmService,
                         FcmProducer fcmProducer,
                         ConversionService conversionService) {
        this.fcmTopicMessageService = fcmTopicMessageService;
        this.fcmTokenMessageService = fcmTokenMessageService;
        this.fcmService = fcmService;
        this.fcmProducer = fcmProducer;
        this.conversionService = conversionService;
    }

    @Override
    @ApiOperation(value = "Realize o processo de unsubscribe em um topic.")
    public void unsubscribe(@PathVariable String organizationId,
                            @PathVariable String topic,
                            @PathVariable String token) {

        topic = topic.concat("_").concat(organizationId);

        log.info("unsubscribe: topic '{}', token: '{}'", topic, token);

        fcmService.unsubscribe(topic, token);
    }

    @Override
    @ApiOperation(value = "Realize o processo de subscribe em um topic.")
    public void subscribe(@PathVariable String organizationId,
                          @PathVariable String topic,
                          @PathVariable String token) {

        topic = topic.concat("_").concat(organizationId);

        log.info("subscribe: topic '{}', token: '{}'", topic, token);

        fcmService.subscribe(topic, token);
    }

    @Override
    @ApiOperation(value = "Realize o envio de uma menssagem para um topic.")
    public Envelope<FcmTopicMessageDTO> send(@PathVariable String organizationId,
                                             @RequestBody SendFcmTopicMessageDTO sendFcmTopicMessageDTO) {

        FcmTopicMessage msg = conversionService.convert(sendFcmTopicMessageDTO, FcmTopicMessage.class);

        msg.setOrganizationId(organizationId);
        msg.setTopic(msg.getTopic().concat("_").concat(organizationId));

        log.info("send: topic '{}', organizationId: '{}', title '{}', body '{}', data '{}'", msg.getTopic(),
                msg.getOrganizationId(), msg.getTitle(), msg.getBody(), msg.getData());

        return
                Envelope
                        .<FcmTopicMessageDTO>builder()
                        .data(conversionService.convert(fcmProducer.send(msg), FcmTopicMessageDTO.class))
                        .build();

    }

    @Override
    @ApiOperation(value = "Realize o envio de uma menssagem para um token.")
    public Envelope<FcmTokenMessageDTO> send(@PathVariable String organizationId,
                                             @RequestBody SendFcmTokenMessageDTO sendFcmTokenMessageDTO) {

        FcmTokenMessage msg = conversionService.convert(sendFcmTokenMessageDTO, FcmTokenMessage.class);

        msg.setOrganizationId(organizationId);

        log.info("send: token '{}', organizationId: '{}', title '{}', body '{}', data '{}'", msg.getToken(),
                msg.getOrganizationId(), msg.getTitle(), msg.getBody(), msg.getData());

        return
                Envelope
                        .<FcmTokenMessageDTO>builder()
                        .data(conversionService.convert(fcmProducer.send(msg), FcmTokenMessageDTO.class))
                        .build();
    }

    @Override
    @ApiOperation(value = "Resgata as informaçãoes de uma menssagem enviada para um topic pelo id.")
    public Envelope<FcmTopicMessageDTO> findByTopicId(@PathVariable String organizationId,
                                                      @PathVariable String id) {

        log.info("findByTopicId: id '{}'", id);

        return
                Envelope
                        .<FcmTopicMessageDTO>builder()
                        .data(conversionService.convert(fcmTopicMessageService.findById(id), FcmTopicMessageDTO.class))
                        .build();

    }

    @Override
    @ApiOperation(value = "Resgata as informaçãoes de uma menssagem enviada para um token pelo id.")
    public Envelope<FcmTokenMessageDTO> findByTokenId(@PathVariable String organizationId,
                                                      @PathVariable String id) {

        log.info("findByTokenId: id '{}'", id);

        return
                Envelope
                        .<FcmTokenMessageDTO>builder()
                        .data(conversionService.convert(fcmTokenMessageService.findById(id), FcmTokenMessageDTO.class))
                        .build();
    }

    @Override
    @ApiOperation(value = "Resgata as informaçãoes de todas as menssagens enviadas para um token.")
    public Envelope<List<FcmTokenMessageDTO>> findAllByToken(@PathVariable String organizationId,
                                                             @PathVariable String token) {

        log.info("findAllByToken: token '{}'", token);

        List<FcmTokenMessageDTO> ret = (List<FcmTokenMessageDTO>)
                conversionService.convert(
                        fcmTokenMessageService.findAllByToken(token),
                        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(FcmTokenMessage.class)),
                        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(FcmTokenMessageDTO.class))
                );

        return
                Envelope
                        .<List<FcmTokenMessageDTO>>builder()
                        .data(ret)
                        .build();
    }

    @Override
    @ApiOperation(value = "Resgata as informaçãoes de todas as menssagens enviadas para um topic.")
    public Envelope<List<FcmTopicMessageDTO>> findAllByTopic(@PathVariable String organizationId,
                                                             @PathVariable String topic) {

        log.info("findAllByTopic: topic '{}'", topic);

        List<FcmTopicMessageDTO> ret = (List<FcmTopicMessageDTO>)
                conversionService.convert(
                        fcmTopicMessageService.findAllByTopic(topic),
                        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(FcmTopicMessage.class)),
                        TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(FcmTopicMessageDTO.class))
                );

        return
                Envelope
                        .<List<FcmTopicMessageDTO>>builder()
                        .data(ret)
                        .build();

    }
}
