package com.cmc.document.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.document.dto.DocumentDTO;
import com.cmc.document.dto.DocumentDTO.DocumentCounter;
import com.cmc.document.service.DocumentService;
import com.cmc.util.dto.ApiResponse;
import com.cmc.util.dto.PaginationResponseDTO;
import com.cmc.util.func.ApiUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documentMgmt")
@RequiredArgsConstructor
public class DocumentManagementController {

	private final DocumentService documentService;
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<DocumentDTO.Response>> createDocument(@Valid @RequestBody DocumentDTO.Request request){
		Integer documentId = documentService.createDocument(request);
		return ApiUtils.buildApiResponse(
				DocumentDTO.Response.builder().id(documentId).build()
				,HttpStatus.CREATED
				,"Create Document successfully"
		);
	}
	
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<PaginationResponseDTO<DocumentDTO.Response>>> searchDocuments(@Valid @RequestBody DocumentDTO.Request request){
		PaginationResponseDTO<DocumentDTO.Response> result = documentService.searchDocument(request);
		return ApiUtils.buildApiResponse(
				result
				,HttpStatus.OK
				,"Found out list documents"
		);
	}
	
	
	@PatchMapping("/edit")
	public ResponseEntity<ApiResponse<DocumentDTO.Response>> editDocument(@Valid @RequestBody DocumentDTO.Request request){
		String documentCode = documentService.editDocument(request);
		return ApiUtils.buildApiResponse(
				DocumentDTO.Response.builder().code(documentCode).build()
				,HttpStatus.CREATED
				,"Edit Document successfully"
		);
	}
	
	@GetMapping("/view/{documentCode}")
	public ResponseEntity<ApiResponse<DocumentDTO.Response>> viewDocumentBycode(@PathVariable String documentCode){
		DocumentDTO.Response result = documentService.getDocumentDetail(documentCode);
		return ApiUtils.buildApiResponse(
				result
				,HttpStatus.CREATED
				,"View  Document successfully"
		);
	}
	

	@GetMapping("/countDocumentByStatus")
	public ResponseEntity<ApiResponse<List<DocumentCounter>>> countDocumentsByStatus(){
		List<DocumentCounter> result = documentService.countDocumentsByStatus();
		return ApiUtils.buildApiResponse(
				result
				,HttpStatus.CREATED
				,"Count Document successfully"
		);
	}
}
