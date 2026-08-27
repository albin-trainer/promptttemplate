package com.albin.promptengineering.dto;

/**
 * Response DTO for review sentiment analysis.
 */
public record ReviewResponse(

        String review,
        String sentiment,
        String explanation

) {
}