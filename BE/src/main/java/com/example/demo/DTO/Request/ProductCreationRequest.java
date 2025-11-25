package com.example.demo.DTO.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreationRequest {
	@NotBlank(message = "REQUIRED_PRODUCT_NAME")
    private String name;

    @Size(max = 500, message = "OVER_MAX_DESCRIPTION_LENGTH")
    private String description;

    @NotNull(message = "REQUIRED_PRODUCT_PRICE")
    @PositiveOrZero(message = "INVALID_PRODUCT_PRICE")
    private BigDecimal price;

    @PositiveOrZero(message = "INVALID_PRODUCT_QUANTITY")
    private int quantity;

    @NotBlank(message = "REQUIRED_PRODUCT_CATEGORY")
    private String category;

    private MultipartFile image;
}
