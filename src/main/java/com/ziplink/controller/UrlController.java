package com.ziplink.controller;

import com.ziplink.dto.ShortenerResponse;
import com.ziplink.dto.urlCustomRequestDTO;
import com.ziplink.dto.urlRequestDTO;
import com.ziplink.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService urlService;


    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("api/shorten")
    public ResponseEntity<ShortenerResponse> shortUrl(@RequestBody @Valid urlRequestDTO request) {
        ShortenerResponse shortenerResponse = urlService.shortUrl(request);
        return ResponseEntity.ok(shortenerResponse);
    }


    @GetMapping("/r/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable @Valid String shortUrl) {
        String originalURL=urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalURL))
                .build();
    }


    @PostMapping("/api/customShorten")
    public ResponseEntity<ShortenerResponse> shortCustomUrl(@RequestBody @Valid urlCustomRequestDTO request) {
        ShortenerResponse shortenerResponse = urlService.customShortUrl(request);
        return ResponseEntity.ok(shortenerResponse);
    }

}
