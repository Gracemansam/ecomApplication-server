package com.sam.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import com.sam.common_constant.CommonConstant;
import com.sam.exception.BusinessException;
import com.sam.exception.ErrorModel;
import com.sam.service.ProductService;
import com.sam.service.ReviewService;
import org.springframework.stereotype.Service;

import com.sam.model.Product;
import com.sam.model.Review;
import com.sam.model.User;
import com.sam.repository.ProductRepository;
import com.sam.repository.ReviewRepository;
import com.sam.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {
	
	private ReviewRepository reviewRepository;
	private ProductService productService;
	private ProductRepository productRepository;
	
	public ReviewServiceImplementation(ReviewRepository reviewRepository,ProductService productService,ProductRepository productRepository) {
		this.reviewRepository=reviewRepository;
		this.productService=productService;
		this.productRepository=productRepository;
	}

	@Override
	public Review createReview(ReviewRequest req,User user)  {
		// TODO Auto-generated method stub
		Product product=productService.findProductById(req.getProductId());
		Review review=new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
//		product.getReviews().add(review);
		productRepository.save(product);
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
