package com.example.demo.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilterRequest {
	private String category;
	private String name;
	private Double minPrice;
	private Double maxPrice;
	private Boolean active;
	@Builder.Default
	private Integer page = 0;
	@Builder.Default
	private Integer size = 10;
}
