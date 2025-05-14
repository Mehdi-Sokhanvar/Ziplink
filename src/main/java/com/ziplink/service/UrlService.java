package com.ziplink.service;

import com.ziplink.dto.ShortenerResponse;
import com.ziplink.dto.urlCustomRequestDTO;
import com.ziplink.dto.urlRequestDTO;
import jakarta.validation.Valid;

public interface UrlService {
    ShortenerResponse shortUrl( urlRequestDTO request);

    String getOriginalUrl(String shortUrl);

    ShortenerResponse customShortUrl(@Valid urlCustomRequestDTO request);
}
