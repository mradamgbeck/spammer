package com.a.spammer.controllers;

import com.a.spammer.services.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class SpamController {

    @Autowired
    SpamService spamService;

    @PostMapping(path = "/spam/{amount}", produces = "application/json")
    public ResponseEntity<List<UUID>> spamEvents(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(spamService.spam(amount));
    }
}
