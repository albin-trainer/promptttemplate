package com.albin.promptengineering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.albin.promptengineering.dto.ReviewRequest;
import com.albin.promptengineering.dto.ReviewResponse;
import com.albin.promptengineering.service.OneShotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/one-shot")
public class OneShotController {

    private final OneShotService oneShotService;

    public OneShotController(OneShotService oneShotService) {
        this.oneShotService = oneShotService;
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewResponse> analyzeReview(
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                oneShotService.analyzeReview(request)
        );
    }
}