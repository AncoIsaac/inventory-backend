package com.isaacAnco.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomApiResponse {
    private String status;
    private Object data;
    private String message; // Nuevo campo para mensajes de error

    // Constructor para respuestas exitosas
    public CustomApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    // Constructor para errores
    public CustomApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}