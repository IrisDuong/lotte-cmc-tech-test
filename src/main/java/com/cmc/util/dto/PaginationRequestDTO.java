package com.cmc.util.dto;

import java.util.List;

public record PaginationRequestDTO(int pageIndex, int size, List<String> sortOrder) {

}
