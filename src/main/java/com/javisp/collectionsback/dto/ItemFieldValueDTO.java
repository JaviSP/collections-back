package com.javisp.collectionsback.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFieldValueDTO {
    private Long id;
    
    @NotNull(message = "Field ID is required")
    private Long fieldId;
    
    private String value;
}
