package com.cmc.util.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum EDocumentStatus {
	DRAFT(1,"Draft"),
	SUBMITTED(2,"Submitted"),
	APPROVED(3,"Approved"),
	REJECTED(4,"Rejected");
	
	private final Integer value;
	private final String label;
	
	public static EDocumentStatus getByValue(Integer value) {
		return Arrays.stream(EDocumentStatus.values())
		.filter(entry-> entry.getValue().equals(value))
		.findFirst().orElse(null);
	}
	
	private EDocumentStatus(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
}
