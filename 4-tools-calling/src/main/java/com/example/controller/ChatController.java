package com.example.controller;

import org.springframework.web.bind.annotation.*;

import com.example.service.AIChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    private final AIChatService aiChatService;

    public ChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping
    public String chat(@RequestBody String prompt) {

        return aiChatService.chat(prompt);
    }

}