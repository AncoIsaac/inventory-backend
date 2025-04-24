package com.isaacAnco.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Data
@Getter
@Setter
public class CustomApiResponsePagination {
    private String status;
    private Long totalItems;
    private int size;
    private int totalPages;
    private int currentPage;
    private Object data;
    private String message; 
}
