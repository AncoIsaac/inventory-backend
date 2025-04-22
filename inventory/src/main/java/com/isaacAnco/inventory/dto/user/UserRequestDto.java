package com.isaacAnco.inventory.dto.user;


import com.isaacAnco.inventory.model.user.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Schema(description = "User's email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Must be a valid email")
    private String email;

    @Schema(description = "User's password (minimum 8 characters)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Schema(description = "User's name", example = "miguel", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 10, message = "Name must be less than 20 characters long")
    private String userName;

    @Schema(description = "User's role", example = "USER", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Role role = Role.USER;
}
