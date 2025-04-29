package com.isaacAnco.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class CustomApiResponse {
    private Object data;
    private String message; // Nuevo campo para mensajes de error
}