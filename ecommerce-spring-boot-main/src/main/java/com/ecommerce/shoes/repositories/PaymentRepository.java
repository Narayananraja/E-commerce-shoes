package com.ecommerce.shoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shoes.entities.OrderPayment;

public interface PaymentRepository extends JpaRepository<OrderPayment, Long> {
	
}
