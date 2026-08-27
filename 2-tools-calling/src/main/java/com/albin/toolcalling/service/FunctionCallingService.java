package com.albin.toolcalling.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.albin.toolcalling.dto.UserRequest;
import com.albin.toolcalling.tool.BookFlightService;
import com.albin.toolcalling.tool.WeatherService;

@Service
public class FunctionCallingService {

    private final ChatClient chatClient;

    public FunctionCallingService(ChatClient.Builder builder,
                                  WeatherService weatherService,BookFlightService bookFlight) {

        this.chatClient = builder
                .defaultTools(weatherService,bookFlight)
                .build();
    }

    public String askQuestion(UserRequest  request) {

        return chatClient.prompt()
                .user(request.question())
                .call()
                .content();
    }
}