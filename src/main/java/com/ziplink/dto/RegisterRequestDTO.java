package com.ziplink.dto;


import jakarta.validation.constraints.*;

public record RegisterRequestDTO
        (
                @NotNull
                @Email
                @NotBlank
                @NotEmpty
                String email,
                @NotNull
                @Min(8)
                @NotBlank
                @NotEmpty
                String password
        ) {
}
