package com.ziplink.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;

@Service
public class SafeBrowsingService {

    @Value("${google.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public  boolean isUrlSafe(String urlToCheck) {
        String endpoint = "https://safebrowsing.googleapis.com/v4/threatMatches:find?key=" + apiKey;

        Map<String, Object> body = Map.of(
                "client", Map.of(
                        "clientId", "null",
                        "clientVersion", "1.5.2"
                ),
                "threatInfo", Map.of(
                        "threatTypes", List.of("MALWARE", "SOCIAL_ENGINEERING", "UNWANTED_SOFTWARE"),
                        "platformTypes", List.of("ANY_PLATFORM"),
                        "threatEntryTypes", List.of("URL"),
                        "threatEntries", List.of(Map.of("url", urlToCheck))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            return response.getBody() == null || response.getBody().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
