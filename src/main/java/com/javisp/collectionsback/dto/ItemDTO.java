package com.javisp.collectionsback.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    
    @NotNull(message = "Collection ID is required")
    private Long collectionId;
    
    private List<ItemFieldValueDTO> fieldValues;
}
