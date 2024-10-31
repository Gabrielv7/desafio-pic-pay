package com.gabriel.desafiopicpay.client;

import com.gabriel.desafiopicpay.client.dto.AuthorizedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "AuthorizerClient", url = "${ms.authorized.url}")
public interface AuthorizedClient {

    @GetMapping
    AuthorizedResponse getAuthorized();

}
