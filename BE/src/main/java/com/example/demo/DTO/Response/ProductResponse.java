package com.example.demo.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	private int quantity;
	private String category;
	private String imagePath;
	private boolean active;
}
