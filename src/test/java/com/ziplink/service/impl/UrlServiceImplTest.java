package com.ziplink.service.impl;

import com.ziplink.dto.ShortenerResponse;
import com.ziplink.dto.urlRequestDTO;
import com.ziplink.exception.custom.UnsafeUrlException;
import com.ziplink.model.Url;
import com.ziplink.model.User;
import com.ziplink.repository.UrlRepository;
import com.ziplink.service.SafeBrowsingService;
import com.ziplink.service.UrlService;
import com.ziplink.util.SecurityUtils;
import com.ziplink.util.UrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.InvalidUrlException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private SafeBrowsingService safeBrowsingService;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlServiceImpl urlService;


    private final User testUser = new User("testUser@gmail.com","123456789");
    private final String testShortUrl = "abc123";
    private final String testOriginUrl = "https://example.com";
    private final String validShortCode = "abc123";
    private final String originalUrl = "https://example.com";
    private final String invalidShortCode = "invalid";

    @BeforeEach
    void setUp() {
    }

    @Test
    void shortUrl_successfulShortening_returnsShortenerResponse() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class);
             MockedStatic<UrlGenerator> urlGeneratorMock = mockStatic(UrlGenerator.class)) {
            urlRequestDTO request=new urlRequestDTO(testShortUrl);
            securityUtilsMock.when(SecurityUtils::getCurrentUser).thenReturn(testUser);
            urlGeneratorMock.when(UrlGenerator::generateUrl).thenReturn(testShortUrl);

            when(safeBrowsingService.isUrlSafe(testOriginUrl)).thenReturn(true);
            when(urlRepository.save(any(Url.class))).thenAnswer(invocation -> invocation.getArgument(0));


            ShortenerResponse response = urlService.shortUrl(request);

            assertNotNull(response);
            assertEquals("http://localhost:8080/r/" + testShortUrl, response.shortUrl());

            verify(safeBrowsingService).isUrlSafe(testOriginUrl);
            verify(urlRepository).save(any(Url.class));
            verifyNoMoreInteractions(safeBrowsingService, urlRepository);
        }
    }

    @Test
    void shortUrl_unsafeUrl_throwsException() {
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            urlRequestDTO request=new urlRequestDTO(testShortUrl);
            securityUtilsMock.when(SecurityUtils::getCurrentUser).thenReturn(testUser);
            when(safeBrowsingService.isUrlSafe(testOriginUrl)).thenReturn(false);
            assertThrows(UnsafeUrlException.class, () -> urlService.shortUrl(request));
            verify(safeBrowsingService).isUrlSafe(testOriginUrl);
            verifyNoInteractions(urlRepository);
        }
    }
    @Test
    void getOriginalUrl_validAndActiveUrl_returnsOriginalUrlAndIncrementsCount() {
        Url mockUrl = new Url();
        mockUrl.setShortCode(validShortCode);
        mockUrl.setOriginalUrl(originalUrl);
        mockUrl.setExpiresAt(LocalDateTime.now().plusDays(1));
        mockUrl.setCountClick(5L);

        when(urlRepository.findByShortCode(validShortCode))
                .thenReturn(Optional.of(mockUrl));
        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        String result = urlService.getOriginalUrl(validShortCode);
        assertEquals(originalUrl, result);
        assertEquals(6, mockUrl.getCountClick());
        verify(urlRepository).findByShortCode(validShortCode);
        verify(urlRepository).save(mockUrl);
    }

}