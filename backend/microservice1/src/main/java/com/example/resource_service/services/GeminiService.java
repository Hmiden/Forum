package com.example.resource_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @jakarta.annotation.PostConstruct
    public void init() {
        if (apiKey == null || apiKey.equals("REPLACE_WITH_YOUR_KEY") || apiKey.isEmpty()) {
            System.err.println("❌ GEMINI API KEY: NOT LOADED! Please check application.properties");
        } else {
            System.out.println("✅ GEMINI API KEY: LOADED SUCCESSFULLY (Starts with: " + apiKey.substring(0, 5) + "...)");
        }
    }

    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3-flash-preview:generateContent?key=";

    public String summarize(String content) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "AI DEBUG: The key is EMPTY or NULL. Value: [" + apiKey + "]";
        }
        if (apiKey.equals("REPLACE_WITH_YOUR_KEY")) {
            return "AI DEBUG: The key is still the DEFAULT placeholder.";
        }

        try {
            String url = GEMINI_API_URL + apiKey;

            // Prepare request body for Gemini API
            Map<String, Object> part = new HashMap<>();
            part.put("text", "Summarize this educational resource in 2 short sentences: " + content);

            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("parts", Collections.singletonList(part));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", Collections.singletonList(contentMap));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Call the API
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            // Parse response (simplified)
            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> candidateContent = (Map<String, Object>) candidate.get("content");
                    List<Map<String, Object>> parts = (List<Map<String, Object>>) candidateContent.get("parts");
                    if (!parts.isEmpty()) {
                        return (String) parts.get(0).get("text");
                    }
                }
            }

            return "Could not generate summary.";

        } catch (Exception e) {
            System.err.println("Gemini API Error: " + e.getMessage());
            return "Error generating AI summary: " + e.getMessage();
        }
    }
}
