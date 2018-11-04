package com.jogsoft.apps.pnr.product.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.jogsoft.apps.pnr.product.exception.ItemNotFoundException;
import com.jogsoft.apps.pnr.product.exception.MisMatchedIdentityException;
import com.jogsoft.apps.pnr.product.exception.ServiceException;
import com.jogsoft.apps.pnr.product.model.dto.ProductDto;
import com.jogsoft.apps.pnr.product.model.entity.Product;
import com.jogsoft.apps.pnr.product.repository.ProductRepository;

@Service
@Validated
public class ProductService {
	
	/* The size of the sublist to be used in pagination */
	public final static int PAGESIZE = 10;
	
	
	private final ProductRepository repository;
	
	
	private final ModelMapper mapper;
	
	public ProductService(ProductRepository repository, ModelMapper mapper){
		this.repository = repository;
		this.mapper = mapper;
	}

	/**
	 * Find and return a list of products as specified by their page number, page numbers starts 
	 * with a zero-based page index.
	 * This returns a paginated list of products
	 * 
	 * @param page A page of products
	 * @param keyword 
	 * @return a sublist (Page) of Products
	 */
	public Page<ProductDto> getProducts(int page) {
		Page<Product> products = repository.findAll(PageRequest.of(page, PAGESIZE));		
		Page<ProductDto> productDtos = mapper.map(products, new TypeToken<Page<ProductDto>>() {}.getType());
		return productDtos;
	}

	/**
	 * Get the Product for the specified product id.
	 * 
	 * @param productId the productId which should not be null
	 * @return found product
	 * @throws ItemNotFoundException if the product is not found 
	 */
	public ProductDto getProduct(@NotNull Long productId) throws ItemNotFoundException {
		Optional<Product> product = repository.findById(productId);
		if(product == null){
			throw new ItemNotFoundException("Product not found for product Id: " + productId);
		}
		return mapper.map(product, ProductDto.class);
	}
	
	
	/**
	 * Search for a product using the specified search keyword
	 * 
	 * @param keyword specified search keyword
	 * @param page zero-based page index.
	 * @return a sublist (Page) of Products
	 */
	public Page<ProductDto> findProduct(String keyword, int page) {
		if(keyword == null || keyword.isEmpty()){
			return this.getProducts(page);
		}
		else{
			Page<Product> products = repository.findByNameOrCategory(PageRequest.of(page, PAGESIZE), keyword);
			return mapper.map(products, new TypeToken<Page<ProductDto>>() {}.getType());
		}
	}
	

	/**
	 * Create a new product
	 * 
	 * @param productDto the product
	 * @return newly created product
	 */
	public ProductDto createProduct(@Valid ProductDto productDto) {
		Product product = mapper.map(productDto, Product.class);
		product = repository.save(product);
		return mapper.map(product, ProductDto.class);
	}

	
	/**
	 * Updates an existing product details 
	 * 
	 * @param productId the id of the product to be updated
	 * @param productDto the product
	 * @return the updated product
	 * 
	 * @throws MisMatchedIdentityException If the productId specified does not match the product Id
	 * @throws ItemNotFoundException if the productId specified does not match any product
	 */
	public ProductDto updateProduct(@NotNull Long productId, @Valid ProductDto productDto) throws MisMatchedIdentityException, ItemNotFoundException {
		if(productDto.getId() == null){
			throw new ServiceException("Product Id used for update is null", null,  HttpStatus.BAD_REQUEST);
		}
		if(productId.longValue() != productDto.getId().longValue()){
			throw new MisMatchedIdentityException(String.format("productId %s and productDto.id %s do not match", productId, productDto.getId()));
		}		
		if(!repository.existsById(productId)){
			throw new ItemNotFoundException(String.format("Product not found for product Id: %s", productId));
		}
		Product product = mapper.map(productDto, Product.class);
		return mapper.map(repository.save(product), ProductDto.class);
	}

	
	
	
	
	

}
