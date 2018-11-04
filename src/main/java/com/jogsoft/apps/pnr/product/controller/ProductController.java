/**
 * 
 */
package com.jogsoft.apps.pnr.product.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jogsoft.apps.pnr.product.exception.ItemNotFoundException;
import com.jogsoft.apps.pnr.product.exception.MisMatchedIdentityException;
import com.jogsoft.apps.pnr.product.exception.ServiceException;
import com.jogsoft.apps.pnr.product.model.dto.ProductDto;
import com.jogsoft.apps.pnr.product.service.ProductService;

/**
 * Product controller for product microservice
 * @author Julius Oduro
 */
@RestController
@RequestMapping("/products")
public class ProductController {
	
	ProductService productService;
	
	public ProductController(ProductService productService){
		this.productService = productService;
	}
	
	@RequestMapping(method= POST)
	public ProductDto createProduct(@RequestBody ProductDto productDto){
		return productService.createProduct(productDto);
	}
	
	
	@RequestMapping(method= GET)
	public Page<ProductDto> getProducts(@RequestParam(name="page", required=false, defaultValue="0")  int page){		
		return productService.getProducts(page);		
	}
	

	@RequestMapping(value="/{productId}", method= GET)
	public ProductDto getProduct(@PathVariable Long productId){		
		try {
			return productService.getProduct(productId);
		} catch (ItemNotFoundException e) {
			throw new ServiceException(e, HttpStatus.NOT_FOUND);
		}		
	}
	
	@RequestMapping(value="/{productId}", method= PUT)
	public ProductDto updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto){		
		try {
			return productService.updateProduct(productId, productDto);
		} catch (ItemNotFoundException e) {
			throw new ServiceException(e, HttpStatus.NOT_FOUND);
		} catch (MisMatchedIdentityException e) {
			throw new ServiceException(e, HttpStatus.BAD_REQUEST);
		}		
	}
	
	
	@RequestMapping(value="/search/{keyword}", method = GET)
	public Page<ProductDto> findProduct(@RequestParam(name="keyword") String keyword, @RequestParam(name="page", required=false, defaultValue="0")  int page){
		return productService.findProduct(keyword, page);
	}
}
