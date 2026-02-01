package com.example.sample.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class LoginRequest {

    @NotBlank(message = "Username is required")
    @Schema(example = "admin", description = "User username")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(example = "password123", description = "User password")
    private String password;
}
