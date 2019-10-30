package com.a.spammer.controllers;

import com.a.spammer.services.SpamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpamControllerTest {

    @Mock
    SpamService spamService;

    @InjectMocks
    SpamController spamController;
    private int spamAmount;
    private ResponseEntity<List<UUID>> response;

    @Before
    public void setup() {
        spamAmount = 3;

        when(spamService.spam(spamAmount)).thenReturn(
                Arrays.asList(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID()
                )
        );

        response = spamController.spamEvents(spamAmount);
    }

    @Test
    public void spamEventsCallsSpamService() {
        verify(spamService).spam(spamAmount);
    }

    @Test
    public void spamEventsReturnsUuidsForEachRequest() {
        assertEquals(spamAmount, response.getBody().size());
    }
}