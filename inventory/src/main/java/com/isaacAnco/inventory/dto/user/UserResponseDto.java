package com.isaacAnco.inventory.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    @Schema(description = "user iid", example = "uuasd")
    private String id;
    @Schema(description = "User's email", example = "user@example.com")
    private String email;
    @Schema(description = "User's username", example = "paquito")
    private String userName;
    @Schema(description = "User's username", example = "true")
    private Boolean isActive;
}
