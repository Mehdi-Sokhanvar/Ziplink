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

    private LocalTime expiresAt;

    @ManyToOne
    private User user;

}
