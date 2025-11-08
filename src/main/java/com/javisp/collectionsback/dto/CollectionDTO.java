package com.javisp.collectionsback.dto;

import com.javisp.collectionsback.model.CollectionField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDTO {
    private Long id;
    
    @NotBlank(message = "Collection name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private List<CollectionFieldDTO> fields;
}
