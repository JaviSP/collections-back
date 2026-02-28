package com.javisp.collectionsback.repository;

import com.javisp.collectionsback.model.CollectionField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionFieldRepository extends JpaRepository<CollectionField, Long> {
    List<CollectionField> findByCollectionId(Long collectionId);
}
