package com.a.spammer.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@CrossOrigin("*")
public class VictimController {

    @PostMapping(path = "/endpoint", produces = "application/json")
    public ResponseEntity<UUID> logEvents(@RequestBody UUID uuid) {
        System.out.println("Received request with uuid: " + uuid);
        try {
            Thread.sleep(1000L, 0);
        } catch (InterruptedException e) {
            System.out.println("Restless Thread, won't sleep.");
        }
        return ResponseEntity.ok(uuid);
    }
}
