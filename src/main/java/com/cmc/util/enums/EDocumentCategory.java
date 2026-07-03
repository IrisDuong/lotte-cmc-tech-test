package com.cmc.util.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum EDocumentCategory {
	USER_GUIDE(1,"User Guideline"),
	LEGAL(2,"Legal"),
	FINAL(3,"Financial"),
	BA(4,"Business & Administrative");
	
	private final Integer value;
	private final String label;
	
	public static EDocumentCategory getByValue(Integer value) {
		return Arrays.stream(EDocumentCategory.values())
		.filter(entry-> entry.getValue().equals(value))
		.findFirst().orElse(null);
	}
	
	private EDocumentCategory(Integer value, String label) {
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
