package com.sam.service.implementation;

import java.time.LocalDateTime;
import java.util.Optional;

import com.sam.common_constant.CommonConstant;
import com.sam.exception.BusinessException;
import com.sam.exception.ErrorModel;
import com.sam.service.CartItemService;
import com.sam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) {
		
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		
		if(user.getId().equals(userId)) {
			
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
			
			return cartItemRepository.save(item);
				
			
		}
		else {
			ErrorModel errorModel = ErrorModel.builder()
					.code("400")
					.message("You can't update  another users cart_item")
					.timestamp(LocalDateTime.now())
					.build();
			throw new BusinessException(errorModel);
		}
		
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		
		return cartItem;
	}
	
	

	@Override
	public void removeCartItem(Long userId,Long cartItemId) {
		
		System.out.println("userId- "+userId+" cartItemId "+cartItemId);
		
		CartItem cartItem=findCartItemById(cartItemId);
		
		User user=userService.findUserById(cartItem.getUserId());
		User reqUser=userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItem.getId());
		}
		else {
			ErrorModel errorModel = ErrorModel.builder()
					.code("400")
					.message("You can't remove another users cart_item")
					.timestamp(LocalDateTime.now())
					.build();
			throw new BusinessException(errorModel);
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId){
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		ErrorModel errorModel = ErrorModel.builder()
				.code(CommonConstant.CART_ITEM_NOT_FOUND_CODE)
				.message(CommonConstant.CART_ITEM_NOT_FOUND)
				.timestamp(LocalDateTime.now())
				.build();
		throw new BusinessException(errorModel);
	}

}
