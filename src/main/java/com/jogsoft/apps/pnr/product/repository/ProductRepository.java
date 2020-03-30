package com.jogsoft.apps.pnr.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jogsoft.apps.pnr.product.model.entity.Product;

import java.util.Map;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Page<Product> findByName(Pageable pageable, String name);

	Page<Product> findByCategory(Pageable pageable, String category);
	
	Page<Product> findByNameAndCategory(Pageable pageable, String name, String category);

	@Query("SELECT p FROM Product p WHERE p.name = test")
	Page<Product> findByKeywords(Pageable pageable, Map<String, String> queryMap);
	
}
