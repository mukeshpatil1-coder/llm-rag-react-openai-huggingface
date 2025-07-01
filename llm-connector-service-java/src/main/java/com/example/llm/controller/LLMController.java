package com.example.llm.controller;

import com.example.llm.dto.PromptRequest;
import com.example.llm.service.LLMService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/llm")
public class LLMController {

    @Autowired
    private LLMService llmService;


    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("✅ LLM Connector Service is running.\nTry POST /llm/query with a prompt.");
    }

    
    @PostMapping("/query")
    public ResponseEntity<Map<String, String>> queryLLM(@RequestBody PromptRequest request) {
        try {
            String response = llmService.query(request.getPrompt(), request.getProvider());
            return ResponseEntity.ok(Map.of("response", response));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

}