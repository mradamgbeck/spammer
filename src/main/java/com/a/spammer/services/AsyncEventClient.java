package com.a.spammer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncEventClient {

    @Value("${victim-service-host}")
    private String victimUrl;

    @Autowired
    private
    RestTemplate restTemplate;

    @Async("asyncExecutor")
    public CompletableFuture<ResponseEntity<UUID>> sendRequest(UUID uuid) {
        ResponseEntity<UUID> responseEntity = ResponseEntity.ok(null);
        try {
            System.out.println("Sending UUID: " + uuid.toString() + " to url: " + victimUrl);
            responseEntity = restTemplate.postForEntity(victimUrl, buildEntity(uuid), UUID.class);
        } catch (Exception e) {
            System.out.println("Encountered exception when sending request: " + e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    private HttpEntity<UUID> buildEntity(UUID uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(uuid, headers);
    }
}
