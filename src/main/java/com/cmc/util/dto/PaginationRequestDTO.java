package com.cmc.util.dto;

import java.util.List;
import java.util.Map;

public record PaginationRequestDTO(int pageIndex, int size, Map<String,String> sortOrder) {

}
