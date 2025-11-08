package com.javisp.collectionsback.dto;

import com.javisp.collectionsback.model.CollectionField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionFieldDTO {
    private Long id;
    
    @NotBlank(message = "Field name is required")
    private String fieldName;
    
    @NotNull(message = "Field type is required")
    private CollectionField.FieldType fieldType;
    
    private Boolean required;
    
    private Integer order;
}
