package com.sam.service;

import com.sam.exception.ProductException;
import com.sam.model.Cart;
import com.sam.model.CartItem;
import com.sam.model.User;
import com.sam.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}
