package com.ecommerce.shoes.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.shoes.entities.Product;
import com.ecommerce.shoes.repositories.ProductRepository;

@Component
public class ProductService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		
		List<Product> allProducts = productRepository.findAll();
		LOGGER.info("Found products -> {}", allProducts);
		
		return allProducts;
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	public void updateProduct(Product product) {
		productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}






	public List<Product> getAllProductsFromProductLine(Long productLineId) {
		
		List<Product> products = productRepository.findByProductLineId(productLineId);
		LOGGER.info("Number of sizes of product {} -> {}", productLineId, products.size());

		return products;
	}
	
	public Product getShoeById(Long productId) {
		
		Optional<Product> optionalShoe = productRepository.findById(productId);
		LOGGER.info("Found product -> {}", optionalShoe);
		
		if (optionalShoe.isPresent()) {
			return optionalShoe.get();
		}
		
		return null;
	}

	public Product findProductById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		return optionalProduct.orElse(null);
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

}
