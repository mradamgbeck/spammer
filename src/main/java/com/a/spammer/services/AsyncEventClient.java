package com.a.spammer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
public class AsyncEventClient {
    @Autowired
    private
    RestTemplate restTemplate;

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<UUID>> sendRequest(UUID uuid){
        return CompletableFuture.completedFuture(
                restTemplate.exchange("url", HttpMethod.POST, buildEntity(uuid), UUID.class)
        );
    }

    private HttpEntity<UUID> buildEntity(UUID uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(uuid, headers);
    }
}
