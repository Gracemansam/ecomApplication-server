package com.sam.service;

import com.sam.model.Cart;
import com.sam.model.CartItem;
import com.sam.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem);
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId);
	
	public CartItem findCartItemById(Long cartItemId);
	
}
