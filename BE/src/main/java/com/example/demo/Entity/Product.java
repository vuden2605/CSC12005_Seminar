package com.example.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
	@Id
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	private int quantity;
	private String category;
	@Builder.Default
	private boolean active = true;
	private String imagePath;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
