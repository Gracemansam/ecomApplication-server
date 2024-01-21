package com.sam.model;

import java.time.LocalDateTime;
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
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends  BaseEntity {
	

	@JsonIgnore
	@ManyToOne
	private Order order;
	
	@ManyToOne
	private Product product;
	
	private String size;
	
	private int quantity;
	
	private Integer price;
	
	private Integer discountedPrice;
	
	private Long userId;
	
	private LocalDateTime deliveryDate;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem orderItem = (OrderItem) o;
		return quantity == orderItem.quantity && Objects.equals(order, orderItem.order) && Objects.equals(product, orderItem.product) && Objects.equals(size, orderItem.size) && Objects.equals(price, orderItem.price) && Objects.equals(discountedPrice, orderItem.discountedPrice) && Objects.equals(userId, orderItem.userId) && Objects.equals(deliveryDate, orderItem.deliveryDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, product, size, quantity, price, discountedPrice, userId, deliveryDate);
	}
}
