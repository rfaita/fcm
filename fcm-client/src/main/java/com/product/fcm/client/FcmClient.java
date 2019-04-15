package com.product.fcm.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 * @author rfaita
 */
@FeignClient(name = "fcm-service", path = "/fcm")
public interface FcmClient extends FcmContract {

}
