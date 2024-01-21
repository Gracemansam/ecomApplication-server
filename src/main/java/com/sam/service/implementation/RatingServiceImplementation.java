package com.sam.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import com.sam.common_constant.CommonConstant;
import com.sam.exception.BusinessException;
import com.sam.exception.ErrorModel;
import com.sam.service.ProductService;
import com.sam.service.RatingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sam.model.Product;
import com.sam.model.Rating;
import com.sam.model.User;
import com.sam.repository.RatingRepository;
import com.sam.request.RatingRequest;

@Service
@RequiredArgsConstructor
public class RatingServiceImplementation implements RatingServices {
	
	private  final RatingRepository ratingRepository;
	private  final ProductService productService;
	
//	public RatingServiceImplementation(RatingRepository ratingRepository,ProductService productService) {
//		this.ratingRepository=ratingRepository;
//		this.productService=productService;
//	}

	@Override
	public Rating createRating(RatingRequest req,User user) {
		
		Product product=productService.findProductById(req.getProductId());
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		// TODO Auto-generated method stub
		return ratingRepository.getAllProductsRating(productId);
	}
	
	

}
