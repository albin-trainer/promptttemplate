package com.example.api;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final ChatClient chatClient;

    public OrderController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {

        return chatClient
                .prompt()
                .system("""
                        You are an order support assistant.

                        Answer the user's question only using
                        the order information provided in the
                        retrieved context.

                        If the required order information is not
                        available, say that you don't know.
                        """)
                .user(question)
                .call()
                .content();
    }
}