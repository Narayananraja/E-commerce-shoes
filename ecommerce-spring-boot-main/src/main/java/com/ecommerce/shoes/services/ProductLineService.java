package com.ecommerce.shoes.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.shoes.entities.ProductLine;
import com.ecommerce.shoes.repositories.ProductLineRepository;

@Component
public class ProductLineService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductLineService.class);

	@Autowired
	ProductLineRepository productLineRepository;
	
	public ProductLine getProductLineById(Long productLineId) {
		
		Optional<ProductLine> optionalProductLine = productLineRepository.findById(productLineId);
		LOGGER.info("Found product line -> {}", optionalProductLine);

		if (optionalProductLine.isPresent()) {
			return optionalProductLine.get();
		}
		
		return null;
		
	}

	public List<ProductLine> findAllProductLines() {
		return productLineRepository.findAll();
	}

	public List<ProductLine> getAllProductLines() {
		return productLineRepository.findAll();
	}


}
