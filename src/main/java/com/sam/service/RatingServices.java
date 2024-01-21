package com.sam.service;

import java.util.List;

import com.sam.exception.ProductException;
import com.sam.model.Rating;
import com.sam.model.User;
import com.sam.request.RatingRequest;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}
