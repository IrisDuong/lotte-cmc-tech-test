package com.cmc.document.service;

import java.util.List;

import com.cmc.document.dto.DocumentDTO;
import com.cmc.document.dto.DocumentDTO.DocumentCounter;
import com.cmc.util.dto.PaginationResponseDTO;

public interface DocumentService {

	Integer createDocument(DocumentDTO.Request request);
	PaginationResponseDTO<DocumentDTO.Response> searchDocument(DocumentDTO.Request request);
	String editDocument(DocumentDTO.Request request);
	DocumentDTO.Response getDocumentDetail(String code);
	List<DocumentCounter> countDocumentsByStatus();
}
