package com.example.demo.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private String name;

    @Size(max = 500, message = "OVER_MAX_DESCRIPTION_LENGTH")
    private String description;

    @PositiveOrZero(message = "INVALID_PRODUCT_PRICE")
    private BigDecimal price;

    @PositiveOrZero(message = "INVALID_PRODUCT_QUANTITY")
    private int quantity;
    private String category;
    private MultipartFile image;
    private Boolean active;
}
