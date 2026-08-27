package com.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.example.tools.EmployeeTools;

@Service
public class AIChatService {

    private final ChatClient chatClient;

    public AIChatService(ChatClient.Builder builder,
                         EmployeeTools employeeTools) {

        this.chatClient = builder
                .defaultTools(employeeTools)
                .build();
    }

    public String chat(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

}