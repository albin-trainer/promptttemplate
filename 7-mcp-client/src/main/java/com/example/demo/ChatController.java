package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final SyncMcpToolCallbackProvider mcpTools;

    public ChatController(
            ChatClient chatClient,
            SyncMcpToolCallbackProvider mcpTools) {

        this.chatClient = chatClient;
        this.mcpTools = mcpTools;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {

        return chatClient
                .prompt()
                .user(question)
                .tools(mcpTools)
                .call()
                .content();
    }
}