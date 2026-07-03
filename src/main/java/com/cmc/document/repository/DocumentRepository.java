package com.cmc.document.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cmc.document.dto.DocumentDTO.DocumentCounter;
import com.cmc.document.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer>, JpaSpecificationExecutor<Document>{

	Boolean existsByCode(String code);
	Optional<Document> findByCode(String code);
	
	@Query("""
			SELECT new com.cmc.document.dto.DocumentDTO$DocumentCounter(COUNT(d.id), d.status)
					FROM Document d
					GROUP BY d.status
			""")
	List<DocumentCounter> countDocumentsByStatus();
	
}
