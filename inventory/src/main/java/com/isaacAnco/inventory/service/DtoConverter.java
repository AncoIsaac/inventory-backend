package com.isaacAnco.inventory.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoConverter {
    private String status;
    private Object data;
    private String message;
}
