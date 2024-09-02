package com.ecommerce.shoes.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shoes.entities.ProductCategory;
import com.ecommerce.shoes.entities.ProductLine;

public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
	
	ProductLine findById(String productLineId);	

	List<ProductLine> findByCategory(ProductCategory category);	

}
