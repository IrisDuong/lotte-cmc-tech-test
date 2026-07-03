package com.cmc.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cmc.document.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer>, JpaSpecificationExecutor<Document>{

	Boolean existsByCode(String code);
}
