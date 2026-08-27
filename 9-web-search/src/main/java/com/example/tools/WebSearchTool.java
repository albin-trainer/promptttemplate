package com.example.tools;

import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WebSearchTool {

    private final RestClient restClient;
    private final String tavilyApiKey;

    public WebSearchTool(
            @Value("${tavily.api-key}") String tavilyApiKey) {

        this.tavilyApiKey = tavilyApiKey;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.tavily.com")
                .build();
    }

    @Tool(
        name = "searchWeb",
        description = """
        		
        Search the internet for current and up-to-date information.

        MUST be used for:
        - latest news
        - today's news
        - current events
        - recent events
        - latest versions
        - current prices
        - real-time information
        - information that may have changed recently

        Do NOT use this tool for stable general knowledge.
        """
    )
    public String searchWeb(
            @ToolParam(description = "The exact topic or question to search on the internet")
            String query) {

        Map<String, Object> request = Map.of(
                "api_key", tavilyApiKey,
                "query", query,
                "search_depth", "basic",
                "max_results", 5
        );

        return restClient.post()
                .uri("/search")
                .body(request)
                .retrieve()
                .body(String.class);
    }
}