package com.ziplink.repository;

import com.ziplink.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID> {

    Optional<Url> findByShortCode(String shortCode);
}
