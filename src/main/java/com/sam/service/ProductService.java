package com.sam.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sam.model.Product;
import com.sam.request.CreateProductRequest;

public interface ProductService {
	
	// only for admin
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId);
	
	public Product updateProduct(Long productId,Product product);
	
	public List<Product> getAllProducts();
	
	// for user and admin both
	public Product findProductById(Long id);
	
	public List<Product> findProductByCategory(String category);
	
	public List<Product> searchProduct(String query);
	
//	public List<Product> getAllProduct(List<String>colors,List<String>sizes,int minPrice, int maxPrice,int minDiscount, String category, String sort,int pageNumber, int pageSize);
	public Page<Product> getAllProduct(String category, List<String>colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount,String sort, String stock, Integer pageNumber, Integer pageSize);
	
	public List<Product> recentlyAddedProduct();
	
	

}
