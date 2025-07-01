package com.example.llm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LLMService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${huggingface.api.key}")
    private String huggingfaceApiKey;

    @Value("${huggingface.model.url}")
    private String hfModelUrl;

    public String query(String prompt, String provider) throws Exception {
        return switch (provider.toLowerCase()) {
            case "openai" -> queryOpenAI(prompt);
            case "huggingface" -> queryHuggingFace(prompt);
            default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
        };
    }

    private String queryOpenAI(String prompt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> message = Map.of("role", "user", "content", prompt);
        Map<String, Object> body = Map.of("model", "gpt-3.5-turbo", "messages", List.of(message));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);
            Map<String, Object> choice = (Map<String, Object>) ((List<?>) response.getBody().get("choices")).get(0);
            Map<String, String> messageResp = (Map<String, String>) choice.get("message");
            return messageResp.get("content");
        } catch (Exception e) {
            throw new Exception("OpenAI API error: " + e.getMessage());
        }
    }

    private String queryHuggingFace(String prompt) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> body = Map.of("inputs", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(huggingfaceApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<List> response = restTemplate.postForEntity(hfModelUrl, request, List.class);
            Map<String, String> result = (Map<String, String>) response.getBody().get(0);
            return result.get("generated_text");
        } catch (Exception e) {
            throw new Exception("HuggingFace API error: " + e.getMessage());
        }
    }
}