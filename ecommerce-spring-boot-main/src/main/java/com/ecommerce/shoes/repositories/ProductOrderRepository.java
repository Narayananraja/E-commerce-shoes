package com.ecommerce.shoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shoes.entities.ProductOrder;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
	
}
