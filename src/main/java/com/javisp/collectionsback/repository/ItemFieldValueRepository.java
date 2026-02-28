package com.javisp.collectionsback.repository;

import com.javisp.collectionsback.model.ItemFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFieldValueRepository extends JpaRepository<ItemFieldValue, Long> {
}
