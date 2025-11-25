package com.example.demo.Service;

import com.example.demo.DTO.Request.ProductCreationRequest;
import com.example.demo.DTO.Request.ProductFilterRequest;
import com.example.demo.DTO.Request.UpdateProductRequest;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Entity.Product;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final MongoTemplate mongoTemplate;
	private final FileUploadService fileUploadService;
	public ProductResponse createProduct(ProductCreationRequest productCreationRequest) throws IOException {
		if (productCreationRequest.getImage() == null || productCreationRequest.getImage().isEmpty()) {
			throw new IllegalArgumentException("Product image is required");
		}
		String imagePath = fileUploadService.uploadFile(productCreationRequest.getImage());

		// Map to entity
		Product product = productMapper.toProduct(productCreationRequest);
		product.setImagePath(imagePath);

		// Save and return
		return productMapper.toProductResponse(productRepository.save(product));
	}
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream()
				.map(productMapper::toProductResponse)
				.collect(Collectors.toList());
	}
	public ProductResponse getProductById(String id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		return productMapper.toProductResponse(product);
	}
	public ProductResponse getProductByName(String name) {
		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(name)) {
				return productMapper.toProductResponse(product);
			}
		}
		throw new RuntimeException("Product not found");
	}
	public Page<ProductResponse> filter(ProductFilterRequest filterRequest) {
		Page<Product> productPage = productRepository.filterProducts(filterRequest);

		List<ProductResponse> productResponses = productPage.getContent().stream()
				.map(productMapper::toProductResponse)
				.toList();

		return new PageImpl<>(productResponses, productPage.getPageable(), productPage.getTotalElements());
	}
	public ProductResponse updateProduct(String id, UpdateProductRequest updateProductRequest) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found"));
		if(updateProductRequest.getName() != null) {
			existingProduct.setName(updateProductRequest.getName());
		}
		if(updateProductRequest.getDescription() != null) {
			existingProduct.setDescription(updateProductRequest.getDescription());
		}
		if(updateProductRequest.getPrice() != null) {
			existingProduct.setPrice(updateProductRequest.getPrice());
		}
		if(updateProductRequest.getQuantity() != 0) {
			existingProduct.setQuantity(updateProductRequest.getQuantity());
		}
		if(updateProductRequest.getCategory() != null) {
			existingProduct.setCategory(updateProductRequest.getCategory());
		}
		if(updateProductRequest.getActive() != null) {
			existingProduct.setActive(updateProductRequest.getActive());
		}
		if (updateProductRequest.getImage() != null) {
			try {
				String imagePath = fileUploadService.uploadFile(updateProductRequest.getImage());
				existingProduct.setImagePath(imagePath);
			} catch (IOException e) {
				throw new RuntimeException("Failed to upload image", e);
			}
		}
		return productMapper.toProductResponse(productRepository.save(existingProduct));
	}
	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}
}
