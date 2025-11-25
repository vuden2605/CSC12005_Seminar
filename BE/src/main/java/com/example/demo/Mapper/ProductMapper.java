package com.example.demo.Mapper;

import com.example.demo.DTO.Request.ProductCreationRequest;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	Product toProduct(ProductCreationRequest request);
	ProductResponse toProductResponse(Product product);
}
