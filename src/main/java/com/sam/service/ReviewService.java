package com.sam.service;

import java.util.List;

import com.sam.exception.ProductException;
import com.sam.model.Review;
import com.sam.model.User;
import com.sam.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}
