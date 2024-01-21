package com.sam.service;

import java.util.List;

import com.sam.model.Address;
import com.sam.model.Order;
import com.sam.model.User;

public interface OrderService {
	
	public Order createOrder(User user, Address shippingAdress);
	
	public Order findOrderById(Long orderId);
	
	public List<Order> usersOrderHistory(Long userId);
	
	public Order placedOrder(Long orderId) ;
	
	public Order confirmedOrder(Long orderId);
	
	public Order shippedOrder(Long orderId);
	
	public Order deliveredOrder(Long orderId);
	
	public Order cancledOrder(Long orderId);
	
	public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId);
	
}
