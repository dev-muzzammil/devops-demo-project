package com.example.sample.DTO.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Registration request payload")
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(example = "johndoe", description = "User username")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Schema(example = "john.doe@example.com", description = "User email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Schema(example = "password123", description = "User password")
    private String password;

    @NotBlank(message = "First name is required")
    @Schema(example = "John", description = "User first name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(example = "Doe", description = "User last name")
    private String lastName;

    @NotBlank(message = "Contact number is required")
    @Schema(example = "1234567890", description = "User contact number")
    private String contactNo;
}
