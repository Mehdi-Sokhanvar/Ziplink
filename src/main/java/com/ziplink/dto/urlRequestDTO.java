package com.ziplink.dto;

import jakarta.validation.constraints.Pattern;

public record urlRequestDTO(
        @Pattern(regexp = "^(http|https)://.*$", message = "url start with http and https")
        String originUrl
) {
}
