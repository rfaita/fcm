package com.product.fcm.example.controller;

import com.product.fcm.client.FcmClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fcm-example")
public class FcmTestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FcmTestController.class);

    @Autowired
    private FcmClient fcmClient;

    @PostMapping("subscribe")
    public void subscribe() {
        LOGGER.info("subscribe");

        fcmClient.subscribe("TESTE1", "teste", "teste123");
    }

    @PostMapping("unsubscribe")
    public void unsubscribe() {
        LOGGER.info("unsubscribe");

        fcmClient.unsubscribe("TESTE1", "teste", "teste123");
    }


}
