package com.javisp.collectionsback.service;

import com.javisp.collectionsback.dto.CollectionDTO;
import com.javisp.collectionsback.dto.CollectionFieldDTO;
import com.javisp.collectionsback.model.Collection;
import com.javisp.collectionsback.model.CollectionField;
import com.javisp.collectionsback.model.User;
import com.javisp.collectionsback.repository.CollectionRepository;
import com.javisp.collectionsback.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionService {
    
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    
    public CollectionService(CollectionRepository collectionRepository, UserRepository userRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
    public List<CollectionDTO> getAllCollections() {
        return collectionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CollectionDTO> getCollectionsByUserId(Long userId) {
        return collectionRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CollectionDTO getCollectionById(Long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found with id: " + id));
        return convertToDTO(collection);
    }
    
    @Transactional
    public CollectionDTO createCollection(CollectionDTO collectionDTO) {
        User user = userRepository.findById(collectionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + collectionDTO.getUserId()));
        
        Collection collection = new Collection();
        collection.setName(collectionDTO.getName());
        collection.setDescription(collectionDTO.getDescription());
        collection.setUser(user);
        
        if (collectionDTO.getFields() != null) {
            List<CollectionField> fields = collectionDTO.getFields().stream()
                    .map(fieldDTO -> convertToFieldEntity(fieldDTO, collection))
                    .collect(Collectors.toList());
            collection.setFields(fields);
        }
        
        Collection savedCollection = collectionRepository.save(collection);
        return convertToDTO(savedCollection);
    }
    
    @Transactional
    public CollectionDTO updateCollection(Long id, CollectionDTO collectionDTO) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found with id: " + id));
        
        collection.setName(collectionDTO.getName());
        collection.setDescription(collectionDTO.getDescription());
        
        if (collectionDTO.getFields() != null) {
            collection.getFields().clear();
            List<CollectionField> fields = collectionDTO.getFields().stream()
                    .map(fieldDTO -> convertToFieldEntity(fieldDTO, collection))
                    .collect(Collectors.toList());
            collection.getFields().addAll(fields);
        }
        
        Collection updatedCollection = collectionRepository.save(collection);
        return convertToDTO(updatedCollection);
    }
    
    @Transactional
    public void deleteCollection(Long id) {
        if (!collectionRepository.existsById(id)) {
            throw new RuntimeException("Collection not found with id: " + id);
        }
        collectionRepository.deleteById(id);
    }
    
    private CollectionDTO convertToDTO(Collection collection) {
        CollectionDTO dto = new CollectionDTO();
        dto.setId(collection.getId());
        dto.setName(collection.getName());
        dto.setDescription(collection.getDescription());
        dto.setUserId(collection.getUser().getId());
        
        if (collection.getFields() != null) {
            List<CollectionFieldDTO> fieldDTOs = collection.getFields().stream()
                    .map(this::convertToFieldDTO)
                    .collect(Collectors.toList());
            dto.setFields(fieldDTOs);
        }
        
        return dto;
    }
    
    private CollectionFieldDTO convertToFieldDTO(CollectionField field) {
        CollectionFieldDTO dto = new CollectionFieldDTO();
        dto.setId(field.getId());
        dto.setFieldName(field.getFieldName());
        dto.setFieldType(field.getFieldType());
        dto.setRequired(field.getRequired());
        dto.setOrder(field.getOrder());
        return dto;
    }
    
    private CollectionField convertToFieldEntity(CollectionFieldDTO dto, Collection collection) {
        CollectionField field = new CollectionField();
        field.setFieldName(dto.getFieldName());
        field.setFieldType(dto.getFieldType());
        field.setRequired(dto.getRequired() != null ? dto.getRequired() : false);
        field.setOrder(dto.getOrder());
        field.setCollection(collection);
        return field;
    }
}
