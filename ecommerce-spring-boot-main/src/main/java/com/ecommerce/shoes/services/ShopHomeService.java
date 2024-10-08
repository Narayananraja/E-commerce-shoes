package com.ecommerce.shoes.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.shoes.entities.ProductCategory;
import com.ecommerce.shoes.entities.ProductLine;
import com.ecommerce.shoes.repositories.ProductLineRepository;

@Component
public class ShopHomeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShopHomeService.class);

	@Autowired
	ProductLineRepository productLineRepository;
	
	public List<ProductLine> getAllMensShoes() {
		
		List<ProductLine> mensShoes = productLineRepository.findByCategory(ProductCategory.MENS_SHOES);
		LOGGER.info("Number of men's shoes -> {}", mensShoes.size());

		return mensShoes;
	}
	
	public List<ProductLine> getAllWomensShoes() {
		
		List<ProductLine> womensShoes = productLineRepository.findByCategory(ProductCategory.WOMENS_SHOES);
		LOGGER.info("Number of women's shoes -> {}", womensShoes.size());

		return womensShoes;
	}

}
