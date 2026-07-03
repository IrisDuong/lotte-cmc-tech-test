package com.cmc.document.service;

import java.util.List;

import com.cmc.document.dto.DocumentDTO;
import com.cmc.util.dto.PaginationResponseDTO;

public interface DocumentService {

	void createDocument(DocumentDTO.Request request);
	PaginationResponseDTO<DocumentDTO.Response> searchDocument(DocumentDTO.Request request);
}
