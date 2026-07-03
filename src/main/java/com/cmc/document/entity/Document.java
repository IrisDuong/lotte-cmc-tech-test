package com.cmc.document.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cmc.auth.entity.User;
import com.cmc.auth.entity.UserRole;
import com.cmc.util.enums.EDocumentCategory;
import com.cmc.util.enums.EDocumentStatus;
import com.cmc.util.service.BaseAuditing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "app_document")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Document extends BaseAuditing{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "code", unique = true)
	private String code;
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	private EDocumentCategory category;
	
	@Enumerated(EnumType.STRING)
	private EDocumentStatus status;
	

}
