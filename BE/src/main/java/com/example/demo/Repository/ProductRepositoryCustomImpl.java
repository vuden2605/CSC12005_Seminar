package com.example.demo.Repository;

import com.example.demo.DTO.Request.ProductFilterRequest;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	private final MongoTemplate mongoTemplate;
	@Override
	public Page<Product> filterProducts(ProductFilterRequest filterRequest) {
		Query query = new Query();

		if(filterRequest.getName() != null) {
			query.addCriteria(Criteria.where("name").regex(filterRequest.getName(), "i"));
		}
		if(filterRequest.getCategory() != null) {
			query.addCriteria(Criteria.where("category").is(filterRequest.getCategory()));
		}
		if(filterRequest.getMinPrice() != null) {
			query.addCriteria(Criteria.where("price").gte(filterRequest.getMinPrice()));
		}
		if(filterRequest.getMaxPrice() != null) {
			query.addCriteria(Criteria.where("price").lte(filterRequest.getMaxPrice()));
		}
		if(filterRequest.getActive() != null) {
			query.addCriteria(Criteria.where("active").is(filterRequest.getActive()));
		}

		Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());
		long total = mongoTemplate.count(query, Product.class);
		List<Product> products = mongoTemplate.find(query.with(pageable), Product.class);

		return new PageImpl<>(products, pageable, total);
	}
}