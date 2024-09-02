package com.ecommerce.shoes.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shoes.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByProductLineId(Long productLineId);	
	
}
