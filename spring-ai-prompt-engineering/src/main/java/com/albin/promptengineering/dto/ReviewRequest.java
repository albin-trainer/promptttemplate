package com.albin.promptengineering.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for review sentiment analysis.
 */
public record ReviewRequest(

        @NotBlank(message = "Review cannot be empty")
        String review
        ) {
}