package com.a.spammer.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class VictimControllerTest {

    @InjectMocks
    VictimController victimController;

    @Test
    public void logEventsReturnsResponseEntityWithUuid(){
        UUID uuid = UUID.randomUUID();
        ResponseEntity<UUID> responseEntity = victimController.logEvents(uuid);
        assertEquals(uuid, responseEntity.getBody());
    }
}