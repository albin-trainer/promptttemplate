package com.example.hr_ai.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
@RestController
public class HRPolicyController {

    private ChatClient chatClient;
    VectorStore vectorStore;
    public HRPolicyController(ChatClient.Builder builder,
                              VectorStore vectorStore) {
//        this.chatClient = builder
//                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore,
//                        SearchRequest.defaults()))
//                .build();
    	this.vectorStore=vectorStore;
    	this.chatClient = builder
    	        .defaultAdvisors(
    	                QuestionAnswerAdvisor.builder(vectorStore)
    	                        .build()).build();
    }
    @PostConstruct
    public void testSearch() {
System.out.println("Post construct ...");
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("office timings")
                        .topK(5)
                        .build());

        docs.forEach(d -> System.out.println(d.getText()));
    }

    @GetMapping("/hr")
    public String hrQandA(@RequestParam(value = "message",
    defaultValue = "What is the Highlight of the hr policy")
                              String message) {
            return chatClient
                    .prompt()
                    .user(message)
                    .call()
                    .content();
    }
}
