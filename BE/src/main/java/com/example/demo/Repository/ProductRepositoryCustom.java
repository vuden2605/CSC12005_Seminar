package com.example.demo.Repository;

import com.example.demo.DTO.Request.ProductFilterRequest;
import com.example.demo.Entity.Product;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
	Page<Product> filterProducts(ProductFilterRequest filterRequest);
}