package com.javisp.collectionsback.controller;

import com.javisp.collectionsback.dto.CollectionDTO;
import com.javisp.collectionsback.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*")
public class CollectionController {
    
    private final CollectionService collectionService;
    
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
    
    @GetMapping
    public ResponseEntity<List<CollectionDTO>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CollectionDTO>> getCollectionsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(collectionService.getCollectionsByUserId(userId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollectionById(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }
    
    @PostMapping
    public ResponseEntity<CollectionDTO> createCollection(@Valid @RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionService.createCollection(collectionDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CollectionDTO> updateCollection(@PathVariable Long id, @Valid @RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.updateCollection(id, collectionDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }
}
