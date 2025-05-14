package com.ziplink.service.impl;

import com.ziplink.dto.ShortenerResponse;
import com.ziplink.dto.urlCustomRequestDTO;
import com.ziplink.dto.urlRequestDTO;
import com.ziplink.exception.custom.ExpiredException;
import com.ziplink.exception.custom.UnsafeUrlException;
import com.ziplink.exception.custom.UrlExistInDatabase;
import com.ziplink.exception.custom.UrlNotFoundException;
import com.ziplink.model.Url;
import com.ziplink.model.User;
import com.ziplink.repository.UrlRepository;
import com.ziplink.service.SafeBrowsingService;
import com.ziplink.service.UrlService;
import com.ziplink.util.SecurityUtils;
import com.ziplink.util.UrlGenerator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final SafeBrowsingService safeBrowsingService;

    public UrlServiceImpl(UrlRepository urlRepository, SafeBrowsingService safeBrowsingService) {
        this.urlRepository = urlRepository;
        this.safeBrowsingService = safeBrowsingService;
    }


    @Override
    public ShortenerResponse shortUrl(urlRequestDTO request) {
        if (!safeBrowsingService.isUrlSafe(request.originUrl())) {
            throw new UnsafeUrlException("This link may be malicious");
        }
        String shorter = UrlGenerator.generateUrl();
        LocalDateTime expireTime = LocalDateTime.now().plusDays(7);
        Url url = new Url(shorter, request.originUrl(), expireTime, SecurityUtils.getCurrentUser(), 0L);
        urlRepository.save(url);
        return new ShortenerResponse("http://localhost:8080/r/".concat(shorter));
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortCode(shortUrl).orElseThrow(() -> new UrlNotFoundException("Url not found"));
        if (url.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredException("URL expired");
        }
        url.setCountClick(url.getCountClick() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    @Transactional
    @Override
    public ShortenerResponse customShortUrl(urlCustomRequestDTO request) {
        if (!safeBrowsingService.isUrlSafe(request.originalURL())) {
            throw new UnsafeUrlException("This link may be malicious");
        }
        urlRepository.findByShortCode(request.custom())
                .ifPresent(existingUrl -> {
                    throw new UrlExistInDatabase("URL already exists in the database.");
                });

        LocalDateTime expireTime = LocalDateTime.now().plusDays(7);
        Url url = new Url(
                request.custom(),
                request.originalURL(),
                expireTime,
                SecurityUtils.getCurrentUser(),
                0L
        );
        urlRepository.save(url);

        return new ShortenerResponse("http://localhost:8080/r/".concat(request.custom()));
    }


}
