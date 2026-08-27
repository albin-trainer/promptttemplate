package com.albin.toolcalling.controller;
import org.springframework.web.bind.annotation.*;

import com.albin.toolcalling.dto.UserRequest;
import com.albin.toolcalling.service.FunctionCallingService;

@RestController
@RequestMapping("/api/function")
public class FunctionCallingController {

    private final FunctionCallingService service;

    public FunctionCallingController(FunctionCallingService service) {
        this.service = service;
    }

    @PostMapping("/assistant")
    public String assistant(@RequestBody UserRequest  request) {

        return service.askQuestion(request);
    }

}