package com.sam.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem  extends  BaseEntity{
	

	
	@JsonIgnore
	@ManyToOne
	private Cart cart;
	
	@ManyToOne
	private Product product;
	
	private String size;
	
	private int quantity;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Long userId;
	



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CartItem cartItem = (CartItem) o;
		return quantity == cartItem.quantity && Objects.equals(cart, cartItem.cart) && Objects.equals(product, cartItem.product) && Objects.equals(size, cartItem.size) && Objects.equals(price, cartItem.price) && Objects.equals(discountedPrice, cartItem.discountedPrice) && Objects.equals(userId, cartItem.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, product, size, quantity, price, discountedPrice, userId);
	}
}
