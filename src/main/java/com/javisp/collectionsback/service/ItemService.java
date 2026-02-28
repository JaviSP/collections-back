package com.javisp.collectionsback.service;

import com.javisp.collectionsback.dto.ItemDTO;
import com.javisp.collectionsback.dto.ItemFieldValueDTO;
import com.javisp.collectionsback.model.Collection;
import com.javisp.collectionsback.model.CollectionField;
import com.javisp.collectionsback.model.Item;
import com.javisp.collectionsback.model.ItemFieldValue;
import com.javisp.collectionsback.repository.CollectionFieldRepository;
import com.javisp.collectionsback.repository.CollectionRepository;
import com.javisp.collectionsback.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    
    private final ItemRepository itemRepository;
    private final CollectionRepository collectionRepository;
    private final CollectionFieldRepository collectionFieldRepository;
    
    public ItemService(ItemRepository itemRepository, 
                      CollectionRepository collectionRepository,
                      CollectionFieldRepository collectionFieldRepository) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
        this.collectionFieldRepository = collectionFieldRepository;
    }
    
    @Transactional(readOnly = true)
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<ItemDTO> getItemsByCollectionId(Long collectionId) {
        return itemRepository.findByCollectionId(collectionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        return convertToDTO(item);
    }
    
    @Transactional
    public ItemDTO createItem(ItemDTO itemDTO) {
        Collection collection = collectionRepository.findById(itemDTO.getCollectionId())
                .orElseThrow(() -> new RuntimeException("Collection not found with id: " + itemDTO.getCollectionId()));
        
        Item item = new Item();
        item.setCollection(collection);
        
        if (itemDTO.getFieldValues() != null) {
            List<ItemFieldValue> fieldValues = itemDTO.getFieldValues().stream()
                    .map(valueDTO -> convertToFieldValueEntity(valueDTO, item))
                    .collect(Collectors.toList());
            item.setFieldValues(fieldValues);
        }
        
        Item savedItem = itemRepository.save(item);
        return convertToDTO(savedItem);
    }
    
    @Transactional
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        
        if (itemDTO.getFieldValues() != null) {
            item.getFieldValues().clear();
            List<ItemFieldValue> fieldValues = itemDTO.getFieldValues().stream()
                    .map(valueDTO -> convertToFieldValueEntity(valueDTO, item))
                    .collect(Collectors.toList());
            item.getFieldValues().addAll(fieldValues);
        }
        
        Item updatedItem = itemRepository.save(item);
        return convertToDTO(updatedItem);
    }
    
    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }
    
    private ItemDTO convertToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setCollectionId(item.getCollection().getId());
        
        if (item.getFieldValues() != null) {
            List<ItemFieldValueDTO> fieldValueDTOs = item.getFieldValues().stream()
                    .map(this::convertToFieldValueDTO)
                    .collect(Collectors.toList());
            dto.setFieldValues(fieldValueDTOs);
        }
        
        return dto;
    }
    
    private ItemFieldValueDTO convertToFieldValueDTO(ItemFieldValue fieldValue) {
        ItemFieldValueDTO dto = new ItemFieldValueDTO();
        dto.setId(fieldValue.getId());
        dto.setFieldId(fieldValue.getField().getId());
        dto.setValue(fieldValue.getValue());
        return dto;
    }
    
    private ItemFieldValue convertToFieldValueEntity(ItemFieldValueDTO dto, Item item) {
        CollectionField field = collectionFieldRepository.findById(dto.getFieldId())
                .orElseThrow(() -> new RuntimeException("Field not found with id: " + dto.getFieldId()));
        
        ItemFieldValue fieldValue = new ItemFieldValue();
        fieldValue.setItem(item);
        fieldValue.setField(field);
        fieldValue.setValue(dto.getValue());
        return fieldValue;
    }
}
