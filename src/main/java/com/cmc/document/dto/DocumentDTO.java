package com.cmc.document.dto;

import com.cmc.util.dto.PaginationRequestDTO;
import com.cmc.util.dto.PaginationResponseDTO;
import com.cmc.util.enums.EDocumentCategory;
import com.cmc.util.enums.EDocumentStatus;

import lombok.Builder;
import lombok.Data;

public class DocumentDTO {

	@Data
	public static class Request{
		private Integer id;
		private String code;
		private String title;
		private String description;
		private Integer statusValue;
		private Integer categoryValue;
		private PaginationRequestDTO pagination;
	}
	

	@Data
	@Builder
	public static class Response{
		private Integer id;
		private String code;
		private String title;
		private String description;
		private EDocumentCategory category;
		private EDocumentStatus status;
	}
}
