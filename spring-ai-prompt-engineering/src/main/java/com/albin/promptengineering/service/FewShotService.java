package com.albin.promptengineering.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.albin.promptengineering.dto.ReviewRequest;
import com.albin.promptengineering.dto.ReviewResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FewShotService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public FewShotService(ChatClient chatClient,
                          ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }

    public ReviewResponse analyzeReview(ReviewRequest request) {

        String prompt = """
                You are an expert customer review analyst.

                Example 1

                Review:
                The laptop hangs frequently and the battery drains quickly.

                Output:
                {
                  "sentiment": "Negative",
                  "explanation": "The customer is unhappy with the laptop's performance and battery life."
                }

                -------------------------------------------------

                Example 2

                Review:
                The phone has an excellent camera and long-lasting battery.

                Output:
                {
                  "sentiment": "Positive",
                  "explanation": "The customer is very satisfied with the camera quality and battery life."
                }

                -------------------------------------------------

                Example 3

                Review:
                The product is okay, but the delivery was delayed.

                Output:
                {
                  "sentiment": "Neutral",
                  "explanation": "The customer has mixed feedback about the product and delivery."
                }

                -------------------------------------------------

                Now analyze the following review.

                Return ONLY valid JSON.

                {
                  "sentiment": "",
                  "explanation": ""
                }

                Rules:
                1. sentiment must be only Positive, Negative or Neutral.
                2. explanation should be exactly one sentence.
                3. Return only valid JSON.
                4. Do not return markdown.
                5. Do not return extra text.

                Review:
                %s
                """.formatted(request.review());

        String aiResponse = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {

            JsonNode json = objectMapper.readTree(aiResponse);

            return new ReviewResponse(
                    request.review(),
                    json.get("sentiment").asText(),
                    json.get("explanation").asText());

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI response", e);
        }
    }
}