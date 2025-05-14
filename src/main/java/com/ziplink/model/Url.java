package com.ziplink.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.sql.Insert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;



@Entity
public class Url extends BaseEntity<UUID> {


    private String shortCode;

    private String originalUrl;

    private LocalDateTime expiresAt;

    @ManyToOne
    private User user;

    private Long countClick;


    public Url(String shortCode, String originalUrl, LocalDateTime expiresAt, User user, Long countClick) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.expiresAt = expiresAt;
        this.user = user;
        this.countClick = countClick;
    }

    public Url() {

    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public User getUser() {
        return user;
    }

    public Long getCountClick() {
        return countClick;
    }

    public void setCountClick(Long countClick) {
        this.countClick = countClick;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
