package com.a.spammer.services;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AsyncEventClientTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AsyncEventClient asyncEventClient;
    private String victimUrl;
    private CompletableFuture<ResponseEntity<UUID>> actualresponse;
    private HttpEntity httpEntity;
    private UUID uuid;
    private HttpHeaders headers;
    private CompletableFuture<ResponseEntity<UUID>> expectedResponse;

    @Before
    public void setup() {
        uuid = UUID.randomUUID();
        victimUrl = "victimUrl";
        ReflectionTestUtils.setField(asyncEventClient, "victimUrl", victimUrl);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(uuid, headers);
        actualresponse = asyncEventClient.sendRequest(uuid);
    }

    @Test
    public void sendRequestCallsRestTemplate() {
        verify(restTemplate).exchange(victimUrl, HttpMethod.POST, httpEntity, UUID.class);
    }

    @Test
    @SneakyThrows
    public void sendRequestReturnsFuture() {
        assertEquals(uuid, actualresponse.get().getBody());
    }
}