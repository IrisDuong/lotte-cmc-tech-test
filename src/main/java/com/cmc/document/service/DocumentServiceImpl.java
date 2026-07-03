package com.cmc.document.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cmc.auth.config.UserDetailsCoreImpl;
import com.cmc.document.dto.DocumentDTO;
import com.cmc.document.dto.DocumentDTO.DocumentCounter;
import com.cmc.document.dto.DocumentDTO.Request;
import com.cmc.document.dto.DocumentDTO.Response;
import com.cmc.document.entity.Document;
import com.cmc.document.repository.DocumentRepository;
import com.cmc.util.dto.PaginationResponseDTO;
import com.cmc.util.enums.EDocumentCategory;
import com.cmc.util.enums.EDocumentStatus;
import com.cmc.util.enums.ESortOrder;
import com.cmc.util.enums.HttpErrorCode;
import com.cmc.util.exception.BaseException;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService{

	private final DocumentRepository documentRepository;

	@Override
	public Integer createDocument(DocumentDTO.Request request) {

		boolean existsByCode = documentRepository.existsByCode(request.getCode());
		if(existsByCode)
			throw new BaseException(HttpErrorCode.DUPLICATED_DATA, "Code of this document existed");
		
		try {
			Document document = Document.builder()
					.title(request.getTitle())
					.code(request.getCode())
					.description(request.getDescription())
					.category(EDocumentCategory.getByValue(request.getCategoryValue()))
					.status(EDocumentStatus.getByValue(request.getStatusValue()))
					.build();
			documentRepository.save(document);
			return document.getId();
		} catch (Exception e) {
			throw new BaseException(HttpErrorCode.INTERNAL_ERROR, "Create Document failed");
		}
				
	}

	@Override
	public PaginationResponseDTO<Response> searchDocument(Request request) {
		Specification<Document> specification = (root,query,cb)->{
			List<Predicate> predicates = List.of(
					cb.or(
							cb.isNotNull(root.get("code")),
							cb.equal(root.get("code"), request.getCode())
					),
					cb.or(
							cb.isNotNull(root.get("code")),
							cb.like(root.get("title"), "%"+ request.getCode()+"%")
					),
					cb.equal(root.get("category"), EDocumentCategory.getByValue(request.getCategoryValue())),
					cb.equal(root.get("status"), EDocumentStatus.getByValue(request.getStatusValue()))
		 );
		
			return cb.and(predicates.toArray(new Predicate[0]));
		};
		
		Sort multiSort = Sort.by(request.getPagination().sortOrder().entrySet().stream()
				.map(entry-> {
					if(entry.getKey().equals(ESortOrder.DESC)) {
						return Sort.Order.desc(entry.getValue());
					}
					return Sort.Order.asc(entry.getValue());
				}).toList()
		);
		Pageable pageable = PageRequest.of(request.getPagination().pageIndex(), request.getPagination().size(),multiSort);
		Page<Document> resultPages = documentRepository.findAll(specification, pageable);
		List<Response> dataResponse = resultPages.getContent().stream()
				.map(result->{
					return Response.builder()
							.id(result.getId())
							.code(request.getCode())
							.title(request.getTitle())
							.description(result.getDescription())
							.category(result.getCategory())
							.status(result.getStatus())
							.build();
				}).toList();
		
		return new PaginationResponseDTO(resultPages, dataResponse);
	}

	@Override
	public String editDocument(Request request) {
		UserDetailsCoreImpl userDetailsCoreImpl = (UserDetailsCoreImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Document exitedDocument = documentRepository.findByCode(request.getCode())
				.orElseThrow(()-> new BaseException(HttpErrorCode.NOT_FOUND, "Document not found"));
		if(Boolean.FALSE.equals(userDetailsCoreImpl.getUsername().equals(exitedDocument.getCreatedBy()))) {
			throw new BaseException(HttpErrorCode.FORBIDDEN, "Permission is denined for editing");
		}

		try {
			exitedDocument.setTitle(request.getTitle());
			exitedDocument.setDescription(request.getDescription());
			exitedDocument.setCategory(EDocumentCategory.getByValue(request.getCategoryValue()));
			exitedDocument.setStatus(EDocumentStatus.getByValue(request.getStatusValue()));
			documentRepository.save(exitedDocument);
			return exitedDocument.getCode();
		} catch (Exception e) {
			throw new BaseException(HttpErrorCode.INTERNAL_ERROR, "Edit Document failed");
		}
	}
	
	@Override
	public Response getDocumentDetail(String code) {
		UserDetailsCoreImpl userDetailsCoreImpl = (UserDetailsCoreImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Document exitedDocument = documentRepository.findByCode(code)
				.orElseThrow(()-> new BaseException(HttpErrorCode.NOT_FOUND, "Document not found"));
		
		if(Boolean.FALSE.equals(userDetailsCoreImpl.getUsername().equals(exitedDocument.getCreatedBy()))) {
			throw new BaseException(HttpErrorCode.FORBIDDEN, "Permission is denined for viewing");
		}
		return Response.builder()
				.id(exitedDocument.getId())
				.code(exitedDocument.getCode())
				.title(exitedDocument.getTitle())
				.description(exitedDocument.getDescription())
				.category(exitedDocument.getCategory())
				.status(exitedDocument.getStatus())
				.build();
	}

	@Override
	public List<DocumentCounter> countDocumentsByStatus() {
		try {
			List<DocumentCounter> result = documentRepository.countDocumentsByStatus();
			return result;
		} catch (Exception e) {
			throw new BaseException(HttpErrorCode.NOT_FOUND, "No counter of documents");
		}
	}
}
