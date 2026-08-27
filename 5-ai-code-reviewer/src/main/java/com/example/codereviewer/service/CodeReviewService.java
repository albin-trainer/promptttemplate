package com.example.codereviewer.service;

import com.example.codereviewer.model.CodeReviewResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class CodeReviewService {

    private final ChatClient chatClient;

    @Value("classpath:prompts/code-review.st")
    private Resource codeReviewPrompt;

    public CodeReviewService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String reviewCode(String code) throws IOException {

        // Read prompt template from .st file
        String template = new String(
                codeReviewPrompt.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );

        // Replace {code} with actual Java code
        PromptTemplate promptTemplate = new PromptTemplate(template);

        String prompt = promptTemplate
                .create(Map.of("code", code))
                .getContents();

        // Send prompt to LLM
        String review = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println(review);
       // return new CodeReviewResponse(review);
        return review;
    }
}