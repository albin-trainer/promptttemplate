package com.albin.toolcalling.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
@Service
public class BookFlightService {
    @Tool(description = "Book flight to city")
public String bookFlight(String destination) {
	return "Booking done to "+destination;
}
}
