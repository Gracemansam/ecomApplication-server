package com.sam.service.implementation;

import java.util.Optional;

import com.sam.service.CartItemService;
import com.sam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sam.exception.CartItemException;
import com.sam.exception.UserException;
import com.sam.model.Cart;
import com.sam.model.CartItem;
import com.sam.model.Product;
import com.sam.model.User;
import com.sam.repository.CartItemRepository;
import com.sam.repository.CartRepository;

@Service
@Lazy
public class CartItemServiceImplementation implements CartItemService {
	
	private CartItemRepository cartItemRepository;
	@Lazy
	private UserService userService;
	private CartRepository cartRepository;

	@Autowired
	public CartItemServiceImplementation(CartItemRepository cartItemRepository) {
		this.cartItemRepository=cartItemRepository;
	}
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		
		if(user.getId().equals(userId)) {
			
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
			
			return cartItemRepository.save(item);
				
			
		}
		else {
			throw new CartItemException("You can't update  another users cart_item");
		}
		
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}
	
	

	@Override
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException {
		
		System.out.println("userId- "+userId+" cartItemId "+cartItemId);
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User user=userService.findUserById(cartItem.getUserId());
		User reqUser=userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItem.getId());
		}
		else {
			throw new UserException("you can't remove anothor users item");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id : "+cartItemId);
	}

}
