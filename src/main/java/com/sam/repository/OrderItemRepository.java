package com.sam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sam.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
