package com.isaacAnco.inventory.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String id;
    private String email;
    private String userName;
    private Boolean isActive;
}
