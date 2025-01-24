package com.example.security_test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class ApiController {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public ApiController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

//    @PostMapping("/update")
//    public void updateVariable(String newValue) {
//        messagingTemplate.convertAndSend("/topic/updates", newValue);
//    }
}