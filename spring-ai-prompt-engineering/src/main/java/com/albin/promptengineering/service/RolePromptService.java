package com.albin.promptengineering.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.albin.promptengineering.dto.RoleReviewRequest;

@Service
public class RolePromptService {

    private final ChatClient chatClient;

    public RolePromptService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String analyzeReview(RoleReviewRequest request) {

        String prompt = """
                You are an experienced %s.

                Analyze the following customer review based on your professional role.

                Review:
                %s

                Give your response in simple language.
                """.formatted(request.role(), request.review());

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}