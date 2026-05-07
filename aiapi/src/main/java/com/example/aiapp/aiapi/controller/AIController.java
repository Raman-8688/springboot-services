package com.example.aiapp.aiapi.controller;

import com.example.aiapp.aiapi.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Autowired
    private AIService aiService;

    @GetMapping("/ask")
    public String askAI(@RequestParam String question) {
        return aiService.getAIResponse(question);
    }
}
