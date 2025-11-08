package com.javisp.collectionsback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collection_fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionField {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fieldName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;
    
    @Column(nullable = false)
    private Boolean required = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", nullable = false)
    @JsonIgnore
    private Collection collection;
    
    @Column(name = "field_order")
    private Integer order;
    
    public enum FieldType {
        TEXT,
        NUMBER,
        DATE,
        IMAGE_URL,
        URL,
        BOOLEAN
    }
}
