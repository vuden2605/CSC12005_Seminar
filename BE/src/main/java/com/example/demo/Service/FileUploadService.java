package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

	@Value("${file.upload-dir:uploads/products}")
	private String uploadDir;
	
	public String uploadFile(MultipartFile file) throws IOException {
		if (file == null || file.isEmpty()) {
			throw new IllegalArgumentException("File is empty or null");
		}

		// Validate file type
		String contentType = file.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new IllegalArgumentException("Only image files are allowed");
		}

		// Validate file size (max 10MB)
		if (file.getSize() > 10 * 1024 * 1024) {
			throw new IllegalArgumentException("File size exceeds maximum limit of 10MB");
		}

		// Create upload directory if not exists
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		// Generate unique filename
		String originalFilename = file.getOriginalFilename();
		String fileExtension = originalFilename != null && originalFilename.contains(".")
				? originalFilename.substring(originalFilename.lastIndexOf("."))
				: "";
		String newFilename = UUID.randomUUID().toString() + fileExtension;

		// Save file
		Path filePath = uploadPath.resolve(newFilename);
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		// Return relative path
		return "

	}

	public void deleteFile(String imagePath) {
		try {
			if (imagePath != null && imagePath.startsWith("/uploads/products/")) {
				String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
				Path filePath = Paths.get(uploadDir).resolve(filename);
				Files.deleteIfExists(filePath);
			}
		} catch (IOException e) {
			// Log error but don't throw exception
			System.err.println("Error deleting file: " + e.getMessage());
		}
	}
}