package com.isaacAnco.inventory.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
public class UpdateUser {
    private String id;
    private String userName;
    private String email;
    private Boolean isActive;
}
