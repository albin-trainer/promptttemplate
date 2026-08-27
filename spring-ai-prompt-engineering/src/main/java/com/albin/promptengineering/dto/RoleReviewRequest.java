package com.albin.promptengineering.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleReviewRequest(

        @NotBlank(message = "Role cannot be empty")
        String role,

        @NotBlank(message = "Review cannot be empty")
        String review

) {
}