package com.albin.toolcalling.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Tool(description = "Get current weather of a city")
    public String getWeather(String city) {

        return switch (city.toLowerCase()) {

            case "bangalore" ->
                    "Bangalore: 26°C, Cloudy";

            case "chennai" ->
                    "Chennai: 34°C, Sunny";

            case "delhi" ->
                    "Delhi: 39°C, Hot";
                    
            case "mumbai" ->
            "Mumbai: 25°C, Heavy Rain";

            default ->
                    "Weather data not available.";
        };
    }
    

    
}