package com.example;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tools.WebSearchTool;

@RestController
public class WebSearchController {

    private final ChatClient chatClient;
    private final WebSearchTool webSearchTool;

    public WebSearchController(
            ChatClient.Builder builder,
            WebSearchTool webSearchTool) {

        this.chatClient = builder.build();
        this.webSearchTool = webSearchTool;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {

        return chatClient.prompt()
                .user(question)
                .tools(webSearchTool)
                .call()
                .content();
    }
}