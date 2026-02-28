package com.javisp.collectionsback.repository;

import com.javisp.collectionsback.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCollectionId(Long collectionId);
}
