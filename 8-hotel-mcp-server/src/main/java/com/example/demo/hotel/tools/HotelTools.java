package com.example.demo.hotel.tools;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class HotelTools {

    @McpTool(description = "Search hotels in a city")
    public String searchHotels(
            @McpToolParam(description = "City name", required = true)
            String city) {

        return "Hotels available in " + city +
               ": Taj Hotel, Marriott Hotel, Holiday Inn";
    }

    @McpTool(description = "Check hotel room availability")
    public String checkAvailability(
            @McpToolParam(description = "Hotel name", required = true)
            String hotelName) {

        return hotelName + " has rooms available";
    }

    @McpTool(description = "Book a hotel room")
    public String bookHotel(
            @McpToolParam(description = "Hotel name", required = true)
            String hotelName,
            @McpToolParam(description = "Number of nights", required = true)
            int nights) {

        return "Successfully booked " + hotelName +
               " for " + nights + " nights";
    }
}