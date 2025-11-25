package com.example.demo.Controller;

import com.example.demo.DTO.Request.ProductCreationRequest;
import com.example.demo.DTO.Request.ProductFilterRequest;
import com.example.demo.DTO.Request.UpdateProductRequest;
import com.example.demo.DTO.Response.ApiResponse;
import com.example.demo.DTO.Response.ProductResponse;
import com.example.demo.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	private final ProductService productService;
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse<ProductResponse> createProduct(@Valid @ModelAttribute ProductCreationRequest request) throws IOException {
		return ApiResponse.<ProductResponse>builder()
				.code(201)
				.message("Product created successfully")
				.data(productService.createProduct(request))
				.build();
	}
	@GetMapping
	public ApiResponse<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> products = productService.getAllProducts();
		return ApiResponse.<java.util.List<ProductResponse>>builder()
				.code(200)
				.message("Products retrieved successfully")
				.data(products)
				.build();
	}
	@GetMapping("/{id}")
	public ApiResponse<ProductResponse> getProductById(@PathVariable String id) {
		ProductResponse productResponse = productService.getProductById(id);
		return ApiResponse.<ProductResponse>builder()
				.code(200)
				.message("Product retrieved successfully")
				.data(productResponse)
				.build();
	}
	@GetMapping("/search")
	public ApiResponse<ProductResponse> getProductByName(@RequestParam String name) {
		ProductResponse productResponse = productService.getProductByName(name);
		return ApiResponse.<ProductResponse>builder()
				.code(200)
				.message("Product retrieved successfully")
				.data(productResponse)
				.build();
	}
	@GetMapping("/filter" )
	public ApiResponse<Page<ProductResponse>> filterProducts(ProductFilterRequest productFilterRequest) {
		return ApiResponse.<Page<ProductResponse>>builder()
				.code(200)
				.message("Products filtered successfully")
				.data(productService.filter(productFilterRequest))
				.build();
	}
	@PatchMapping("/{id}")
	public ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @ModelAttribute @Valid UpdateProductRequest updateProductRequest) {
		ProductResponse productResponse = productService.updateProduct(id,updateProductRequest);
		return ApiResponse.<ProductResponse>builder()
				.code(200)
				.message("Product updated successfully")
				.data(productResponse)
				.build();
	}
	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteProduct(@PathVariable String id) {
		productService.deleteProduct(id);
		return ApiResponse.<Void>builder()
				.code(200)
				.message("Product deleted successfully")
				.build();
	}
}
