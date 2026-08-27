package com.albin.promptengineering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.albin.promptengineering.dto.ReviewRequest;
import com.albin.promptengineering.dto.ReviewResponse;
import com.albin.promptengineering.service.FewShotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/few-shot")
public class FewShotController {

    private final FewShotService fewShotService;

    public FewShotController(FewShotService fewShotService) {
        this.fewShotService = fewShotService;
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewResponse> analyzeReview(
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                fewShotService.analyzeReview(request)
        );
    }
}