package com.albin.promptengineering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.albin.promptengineering.dto.RoleReviewRequest;
import com.albin.promptengineering.service.RolePromptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/role")
public class RolePromptController {

    private final RolePromptService rolePromptService;

    public RolePromptController(RolePromptService rolePromptService) {
        this.rolePromptService = rolePromptService;
    }

    @PostMapping("/review")
    public ResponseEntity<String> analyzeReview(
            @Valid @RequestBody RoleReviewRequest request) {

        return ResponseEntity.ok(
                rolePromptService.analyzeReview(request)
        );
    }
}