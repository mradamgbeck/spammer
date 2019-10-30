package com.a.spammer.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class SpamService {

    @Autowired
    private
    AsyncEventClient asyncEventClient;

    public List<UUID> spam(int amount) {
        List<UUID> eventIds = new ArrayList<>();
        List<CompletableFuture<ResponseEntity<UUID>>> futures = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println("Sending UUID: " + uuid.toString());
            futures.add(asyncEventClient.sendRequest(uuid));
        }

        for (CompletableFuture<ResponseEntity<UUID>> future : futures) {
            try {
                eventIds.add(future.get().getBody());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Exception occurred whilst unwrapping future: \n" + e);
            }
        }
        System.out.println("Successfully processed " + eventIds.size() + " requests.");
        return eventIds;
    }
}
