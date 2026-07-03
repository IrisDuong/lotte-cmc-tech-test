package com.cmc.document.dto;

import com.cmc.util.dto.PaginationRequestDTO;
import com.cmc.util.dto.PaginationResponseDTO;
import com.cmc.util.enums.EDocumentCategory;
import com.cmc.util.enums.EDocumentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class DocumentDTO {

	@Data
	public static class Request{
		private Integer id;
		
		@NotBlank(message = "Code of document is required")
		private String code;
		private String title;
		private String description;

		@NotNull(message = "Status's value of document is required")
		private Integer statusValue;
		

		@NotNull(message = "Category's value of document is required")
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
	
	@Data
	public static class DocumentCounter{
		private Long numsOfDocument;
		private String documentStatus;
		public DocumentCounter(Long numsOfDocument, EDocumentStatus documentStatus) {
			super();
			this.numsOfDocument = numsOfDocument;
			this.documentStatus = documentStatus.getLabel();
		}
	}
}
