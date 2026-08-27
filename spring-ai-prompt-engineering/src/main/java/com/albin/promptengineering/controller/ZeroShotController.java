package com.albin.promptengineering.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.albin.promptengineering.dto.ReviewRequest;
import com.albin.promptengineering.dto.ReviewResponse;
import com.albin.promptengineering.service.ZeroShotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/zero-shot")
public class ZeroShotController {

    private final ZeroShotService zeroShotService;

    public ZeroShotController(ZeroShotService zeroShotService) {
        this.zeroShotService = zeroShotService;
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewResponse> analyzeReview(
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                zeroShotService.analyzeReview2(request)
        );
    }

}