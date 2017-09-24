package com.jogsoft.apps.pnr.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jogsoft.apps.pnr.product.model.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{
	
	Page<Product> findByNameOrCategory(Pageable pageable, String keyword);

}
