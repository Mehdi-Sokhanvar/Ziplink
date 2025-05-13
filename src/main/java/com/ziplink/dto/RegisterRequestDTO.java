package com.ziplink.dto;


import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO
        (
                @NotNull

                String email,
                String password
        ) {
}
