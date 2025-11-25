# 🚀 MongoDB Backend Project - Complete Guideline

## 📋 Mục lục
1. [Tổng quan dự án](#tổng-quan-dự-án)
2. [Tech Stack](#tech-stack)
3. [Cấu trúc dự án](#cấu-trúc-dự-án)
4. [Setup và chạy project](#setup-và-chạy-project)
5. [API Endpoints](#api-endpoints)
6. [MongoDB Schema](#mongodb-schema)
7. [Các tính năng chính](#các-tính-năng-chính)
8. [Best Practices được áp dụng](#best-practices-được-áp-dụng)
9. [Tài liệu tham khảo](#tài-liệu-tham-khảo)

---

## 🎯 Tổng quan dự án

Dự án này là một **REST API Backend** được xây dựng bằng **Spring Boot** và **MongoDB**, demo các tính năng cơ bản của MongoDB trong một ứng dụng quản lý sản phẩm (Product Management System).

### Mục đích
- Demo các thao tác CRUD với MongoDB
- Showcase các tính năng query, filter, pagination
- Upload và quản lý file (hình ảnh sản phẩm)
- Best practices trong Spring Boot + MongoDB

---

## 🛠 Tech Stack

| Technology | Version | Mục đích |
|------------|---------|----------|
| **Java** | 21 | Programming Language |
| **Spring Boot** | 3.5.7 | Framework chính |
| **MongoDB** | Latest | NoSQL Database |
| **Maven** | Latest | Build tool |
| **Lombok** | 1.18.32 | Giảm boilerplate code |
| **MapStruct** | 1.6.0 | Object mapping (DTO ↔ Entity) |
| **Spring Security** | Included | Security & CORS config |
| **Spring Validation** | Included | Input validation |

---

## 📁 Cấu trúc dự án

```
BE/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── Configure/           # Configuration classes
│   │   │   │   ├── SecurityConfig.java    # CORS & Security
│   │   │   │   └── WebConfig.java         # Static resource mapping
│   │   │   │
│   │   │   ├── Controller/          # REST Controllers
│   │   │   │   └── ProductController.java
│   │   │   │
│   │   │   ├── DTO/                 # Data Transfer Objects
│   │   │   │   ├── Request/
│   │   │   │   │   ├── ProductCreationRequest.java
│   │   │   │   │   ├── ProductFilterRequest.java
│   │   │   │   │   └── UpdateProductRequest.java
│   │   │   │   └── Response/
│   │   │   │       ├── ApiResponse.java
│   │   │   │       └── ProductResponse.java
│   │   │   │
│   │   │   ├── Entity/              # MongoDB Documents
│   │   │   │   └── Product.java
│   │   │   │
│   │   │   ├── Exception/           # Exception handling
│   │   │   │   ├── AppException.java
│   │   │   │   ├── ErrorCode.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   ├── Mapper/              # MapStruct mappers
│   │   │   │   └── ProductMapper.java
│   │   │   │
│   │   │   ├── Repository/          # Data access layer
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── ProductRepositoryCustom.java
│   │   │   │   └── ProductRepositoryCustomImpl.java
│   │   │   │
│   │   │   ├── Service/             # Business logic
│   │   │   │   ├── ProductService.java
│   │   │   │   └── FileUploadService.java
│   │   │   │
│   │   │   └── DemoApplication.java # Main application
│   │   │
│   │   └── resources/
│   │       └── application.yml      # Configuration
│   │
│   └── test/                        # Unit tests
│
├── uploads/                         # Uploaded images (runtime)
└── pom.xml                          # Maven dependencies
```

---

## 🚀 Setup và chạy project

### 1. Prerequisites

Đảm bảo đã cài đặt:
- **Java 21** (JDK)
- **Maven** 3.8+
- **MongoDB** (local hoặc Docker)
- **IDE** (IntelliJ IDEA hoặc Eclipse)

### 2. Cài đặt MongoDB

#### Option A: Dùng Docker (Recommended)
```bash
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=12345 \
  mongo:latest
```

#### Option B: Cài đặt local
- Download MongoDB từ [trang chính thức](https://www.mongodb.com/try/download/community)
- Tạo user với credentials: `admin/12345`

### 3. Clone và setup project

```bash
# Clone repo
git clone https://github.com/vuden2605/CSC12005_Seminar.git
cd CSC12005_Seminar/BE

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

### 4. Verify kết nối

- Backend chạy tại: `http://localhost:8080`
- MongoDB: `mongodb://admin:12345@localhost:27017/demoMongoDB`

Test bằng Postman hoặc curl:
```bash
curl http://localhost:8080/products
```

---

## 🌐 API Endpoints

### Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/products` | Tạo sản phẩm mới | `multipart/form-data` |
| **GET** | `/products` | Lấy tất cả sản phẩm | - |
| **GET** | `/products/{id}` | Lấy sản phẩm theo ID | - |
| **GET** | `/products/search?name={name}` | Tìm sản phẩm theo tên | - |
| **GET** | `/products/filter` | Lọc và phân trang sản phẩm | Query params |
| **PATCH** | `/products/{id}` | Cập nhật sản phẩm | `multipart/form-data` |
| **DELETE** | `/products/{id}` | Xóa sản phẩm | - |

### 📝 Chi tiết từng endpoint

#### 1. **POST /products** - Tạo sản phẩm

**Request (multipart/form-data):**
```
name: "iPhone 15 Pro"
description: "Flagship phone from Apple"
price: 999.99
quantity: 50
category: "Electronics"
image: [File]
```

**Response:**
```json
{
  "code": 201,
  "message": "Product created successfully",
  "data": {
    "id": "674528a3f1234567890abcde",
    "name": "iPhone 15 Pro",
    "description": "Flagship phone from Apple",
    "price": 999.99,
    "quantity": 50,
    "category": "Electronics",
    "imagePath": "/uploads/products/uuid-filename.jpg",
    "active": true
  }
}
```

#### 2. **GET /products** - Lấy tất cả sản phẩm

**Response:**
```json
{
  "code": 200,
  "message": "Products retrieved successfully",
  "data": [
    {
      "id": "674528a3f1234567890abcde",
      "name": "iPhone 15 Pro",
      "price": 999.99,
      "quantity": 50,
      "category": "Electronics",
      "imagePath": "/uploads/products/uuid-filename.jpg",
      "active": true
    }
  ]
}
```

#### 3. **GET /products/filter** - Lọc và phân trang

**Query Parameters:**
- `category` (optional): Lọc theo danh mục
- `name` (optional): Tìm kiếm theo tên (case-insensitive, regex)
- `minPrice` (optional): Giá tối thiểu
- `maxPrice` (optional): Giá tối đa
- `active` (optional): Trạng thái active (true/false)
- `page` (default: 0): Trang hiện tại
- `size` (default: 10): Số items mỗi trang

**Example:**
```
GET /products/filter?category=Electronics&minPrice=500&maxPrice=1500&page=0&size=10
```

**Response:**
```json
{
  "code": 200,
  "message": "Products filtered successfully",
  "data": {
    "content": [...],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10
    },
    "totalElements": 25,
    "totalPages": 3
  }
}
```

#### 4. **PATCH /products/{id}** - Cập nhật sản phẩm

**Request (multipart/form-data):**
```
name: "iPhone 15 Pro Max" (optional)
price: 1199.99 (optional)
quantity: 30 (optional)
active: false (optional)
image: [File] (optional)
```

**Response:** Tương tự POST response

#### 5. **DELETE /products/{id}** - Xóa sản phẩm

**Response:**
```json
{
  "code": 200,
  "message": "Product deleted successfully"
}
```

---

## 🗄️ MongoDB Schema

### Collection: `products`

```javascript
{
  "_id": ObjectId("674528a3f1234567890abcde"),
  "name": "iPhone 15 Pro",
  "description": "Flagship phone from Apple",
  "price": NumberDecimal("999.99"),
  "quantity": 50,
  "category": "Electronics",
  "imagePath": "/uploads/products/uuid-filename.jpg",
  "active": true,
  "createdAt": ISODate("2024-11-25T10:30:00Z"),
  "updatedAt": ISODate("2024-11-25T10:30:00Z"),
  "_class": "com.example.demo.Entity.Product"
}
```

### Index Recommendations

Để tối ưu performance, nên tạo các index sau:

```javascript
// Tạo index cho tìm kiếm theo tên
db.products.createIndex({ "name": "text" })

// Index cho filter theo category
db.products.createIndex({ "category": 1 })

// Compound index cho filter phức tạp
db.products.createIndex({ "category": 1, "price": 1, "active": 1 })

// Index cho createdAt (hữu ích cho sorting)
db.products.createIndex({ "createdAt": -1 })
```

---

## ⚡ Các tính năng chính

### 1. **Custom MongoDB Query với MongoTemplate**

File: `ProductRepositoryCustomImpl.java`

Sử dụng `MongoTemplate` để build dynamic queries:

```java
Query query = new Query();

// Regex search (case-insensitive)
if(filterRequest.getName() != null) {
    query.addCriteria(Criteria.where("name").regex(filterRequest.getName(), "i"));
}

// Exact match
if(filterRequest.getCategory() != null) {
    query.addCriteria(Criteria.where("category").is(filterRequest.getCategory()));
}

// Range query
if(filterRequest.getMinPrice() != null) {
    query.addCriteria(Criteria.where("price").gte(filterRequest.getMinPrice()));
}
```

**💡 Demo MongoDB Features:**
- **Regex search**: Tìm kiếm linh hoạt
- **Range queries**: Filter theo khoảng giá
- **Pagination**: Phân trang với `Pageable`
- **Dynamic queries**: Build query dựa trên input

### 2. **File Upload System**

File: `FileUploadService.java`

**Features:**
- Upload hình ảnh sản phẩm
- Validation: Type check, size limit (10MB)
- UUID filename để tránh conflict
- Static file serving qua `/uploads/products/**`

**Flow:**
```
Client → MultipartFile → Validation → UUID filename → Save to disk → Return path
```

### 3. **Exception Handling**

File: `GlobalExceptionHandler.java`, `ErrorCode.java`

**Centralized error handling:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
```

**Custom error codes:**
```java
PRODUCT_NOT_FOUND(2001, "Product not found", HttpStatus.NOT_FOUND)
REQUIRED_PRODUCT_NAME(2002, "REQUIRED_PRODUCT_NAME", HttpStatus.BAD_REQUEST)
```

### 4. **Input Validation**

File: `ProductCreationRequest.java`

```java
@NotBlank(message = "REQUIRED_PRODUCT_NAME")
private String name;

@PositiveOrZero(message = "INVALID_PRODUCT_PRICE")
private BigDecimal price;
```

Validation errors tự động map sang `ErrorCode` qua `GlobalExceptionHandler`.

### 5. **DTO Mapping với MapStruct**

File: `ProductMapper.java`

```java
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);
    ProductResponse toProductResponse(Product product);
}
```

**Benefits:**
- Type-safe mapping
- Auto-generated implementation
- Better performance than reflection-based mappers

---

## ✅ Best Practices được áp dụng

### 1. **Layered Architecture**

```
Controller → Service → Repository → MongoDB
     ↓          ↓          ↓
   DTO      Business    Data Access
           Logic
```

- **Separation of concerns**
- **Single Responsibility Principle**
- Dễ test và maintain

### 2. **DTO Pattern**

- **Request DTOs**: Validation input
- **Response DTOs**: Control output format
- **Entity**: Internal representation
- **Mapper**: Clean conversion

### 3. **API Response Standardization**

```json
{
  "code": 200,
  "message": "Success message",
  "data": { ... }
}
```

Mọi API đều follow format này → Frontend dễ handle.

### 4. **Custom Repository Pattern**

```java
public interface ProductRepository extends MongoRepository<Product, String>, 
                                           ProductRepositoryCustom {
    // Spring Data methods + Custom methods
}
```

Kết hợp:
- Spring Data JPA queries
- Custom MongoTemplate queries

### 5. **Security Configuration**

```java
// Disable CSRF for REST API
.csrf(csrf -> csrf.disable())

// CORS cho multiple origins
.allowedOrigins(List.of("http://localhost:5173", "http://localhost:3000"))
```

---

## 📚 Tài liệu tham khảo

- [Spring Data MongoDB Reference](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [MongoDB Java Driver](https://www.mongodb.com/docs/drivers/java/sync/current/)
- [MapStruct Documentation](https://mapstruct.org/documentation/stable/reference/html/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)

---

## 🤝 Contributing

Nếu muốn contribute:
1. Fork repo
2. Tạo branch mới: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -m 'Add new feature'`
4. Push: `git push origin feature/new-feature`
5. Tạo Pull Request

---

## 📝 Notes

- Project này để demo MongoDB, nên chưa implement authentication/authorization đầy đủ
- Production environment nên thêm JWT, role-based access control
- Nên thêm logging với SLF4J/Logback
- Consider thêm Swagger/OpenAPI documentation

---

**Happy Coding! 🎉**