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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Before
    public void setup() {
        uuid = UUID.randomUUID();
        victimUrl = "victimUrl";
        ReflectionTestUtils.setField(asyncEventClient, "victimUrl", victimUrl);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(uuid, headers);

        when(restTemplate.exchange(victimUrl, HttpMethod.POST, httpEntity, UUID.class))
                .thenReturn(ResponseEntity.ok(uuid));

    }

    @Test
    public void sendRequestCallsRestTemplate() {
        actualresponse = asyncEventClient.sendRequest(uuid);
        verify(restTemplate).exchange(victimUrl, HttpMethod.POST, httpEntity, UUID.class);
    }

    @Test
    @SneakyThrows
    public void sendRequestReturnsFutureWithUuid() {
        actualresponse = asyncEventClient.sendRequest(uuid);
        assertEquals(uuid, actualresponse.get().getBody());
    }

    @Test
    public void sendRequestIgnoresExceptions(){
        when(restTemplate.exchange(victimUrl, HttpMethod.POST, httpEntity, UUID.class))
                .thenThrow(new HttpServerErrorException(HttpStatus.I_AM_A_TEAPOT));
        actualresponse = asyncEventClient.sendRequest(uuid);


    }
}