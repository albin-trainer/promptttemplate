package com.example.codereviewer.controller;

import com.example.codereviewer.model.CodeReviewResponse;
import com.example.codereviewer.service.CodeReviewService;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class CodeReviewController {

    private final CodeReviewService codeReviewService;

    public CodeReviewController(CodeReviewService codeReviewService) {
        this.codeReviewService = codeReviewService;
    }

    @PostMapping(
          
            consumes = MediaType.TEXT_PLAIN_VALUE
    )
    public String review(
            @RequestBody String  code) throws IOException {

        return codeReviewService.reviewCode(code);
    }
}