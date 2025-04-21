package com.isaacAnco.inventory.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
    @Schema(description = "User's email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Must be a valid email")
    private String email;

    @Schema(description = "User's password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
