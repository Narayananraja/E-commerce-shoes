package com.ecommerce.shoes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecommerce.shoes.entities.ProductLine;
import com.ecommerce.shoes.entities.ProductSizes;
import com.ecommerce.shoes.services.ProductLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.shoes.entities.Product;
import com.ecommerce.shoes.entities.ProductCategory;
import com.ecommerce.shoes.services.ProductService;
import javax.validation.Valid;
@Controller
public class ProductController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductLineService productLineService;
	
	@PostMapping(path="/buy_product", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addProductToCart(@RequestParam MultiValueMap<String, String> userInfo, Model model) {
		
		Map<String, String> cartProductDetails = userInfo.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

		LOGGER.info("Cart product -> {}", cartProductDetails);
		LOGGER.info("Cart product id -> {}", cartProductDetails.get("productId"));
		LOGGER.info("Cart product amount -> {}", cartProductDetails.get("amount"));
		LOGGER.info("Cart product price -> {}", cartProductDetails.get("price"));
		
		int amount = Integer.parseInt(cartProductDetails.get("amount"));
		float price = Float.parseFloat(cartProductDetails.get("price"));
		float shipping = 5.00f;
		float tax = 1.00f;
		float total = amount * price + shipping + tax;
		
		model.addAttribute("productId", cartProductDetails.get("productId"));
		model.addAttribute("amount", cartProductDetails.get("amount"));
		model.addAttribute("price", price);
		model.addAttribute("subtotal", amount * price);
		model.addAttribute("shipping", shipping);
		model.addAttribute("tax", tax);
		model.addAttribute("total", total);
		
		return "checkout";
	}
	
	@PostMapping(path="/filter_products_category", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String filterProductsByCategory(@RequestParam MultiValueMap<String, String> filterCriteria, Model model) {
		
		if (null == filterCriteria.get("category").get(0)) {
			model.addAttribute("message", "Product filter failed");
	        return "adminError";
		}
		
		LOGGER.info("Given category -> {}", filterCriteria.get("category").get(0));
		
		List<Product> products = productService.getAllProducts();
		LOGGER.info("Found products -> {}", products);

		List<Product> filteredProducts = new ArrayList<>(); 
		
		for (Product product : products) {
			LOGGER.info("Product category -> {}", product.getProductLine().getCategory());
			if (filterCriteria.get("category").get(0).equals("MENS_SHOES") 
					&& product.getProductLine().getCategory().equals(ProductCategory.MENS_SHOES)) {
				filteredProducts.add(product);
			}
			else if (filterCriteria.get("category").get(0).equals("WOMENS_SHOES")
					&& product.getProductLine().getCategory().equals(ProductCategory.WOMENS_SHOES)) {
				filteredProducts.add(product);
			}
		}
		
		LOGGER.info("Filtered products -> {}", filteredProducts);
		
		if (filteredProducts.size() >= 1) {
			model.addAttribute("products", filteredProducts);
			return "productList";
		}

		model.addAttribute("message", "Product filter failed");
        return "adminError";
	}
	
	@GetMapping(path="/all_products")
	public String showAllProducts(Model model) {
				
		List<Product> allProducts = productService.getAllProducts();
		LOGGER.info("Found products -> {}", allProducts);
		
		if (null != allProducts) {
			model.addAttribute("products", allProducts);
			return "productList";
		}

		model.addAttribute("message", "Loading products failed");
        return "adminError";
	}

//	@GetMapping(path="/editProduct")
//	public String showEditProductPage(@RequestParam("id") Long id, Model model) {
//		Product product = productService.getProductById(id);
//		model.addAttribute("product", product);
//		return "editProduct";
//	}
//	@PostMapping(path="/updateProduct")
//	public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult result) {
//		if (result.hasErrors()) {
//			return "editProduct"; // Return to the edit page with errors
//		}
//		productService.updateProduct(product);
//		return "redirect:/all_products"; // Redirect to the product list
//	}

	@PostMapping(path="/deleteProduct")
	public String deleteProduct(@RequestParam("id") Long id) {
		productService.deleteProduct(id);
		return "redirect:/all_products"; // Redirect to the product list
	}

//	@GetMapping(path="/addProduct")
//	public String addProduct(Model model) {
//		// Optionally, add data to the model if needed, e.g., ProductLines
//		return "addProduct"; // Return the name of the HTML template for the add product page
//	}

	@GetMapping(path = "/editProduct")
	public String showEditProductPage(@RequestParam("id") Long id, Model model) {
		Product product = productService.findProductById(id);
		List<ProductLine> productLines = productLineService.findAllProductLines();

		model.addAttribute("product", product);
		model.addAttribute("productLines", productLines);
		model.addAttribute("sizes", ProductSizes.values());  // Pass the enum values

		return "editProduct";
	}

	@PostMapping(path = "/updateProduct")
	public String updateProduct(@ModelAttribute Product product, @RequestParam("id") Long id) {
//		productService.saveProduct(product);
//		return "redirect:/all_products";
		// Check if product exists
		if (id != null) {
			// Fetch the existing product
			Product existingProduct = productService.findProductById(id);
			if (existingProduct != null) {
				// Update the fields
				existingProduct.setSize(product.getSize());
				existingProduct.setInStockAmount(product.getInStockAmount());
				existingProduct.setProductLine(product.getProductLine());

				// Save the updated product
				productService.saveProduct(existingProduct);
			}
		} else {
			// This case should ideally not happen when updating
			// But in case it does, handle by saving as a new product
			productService.saveProduct(product);
		}

		return "redirect:/all_products"; //
	}

	@GetMapping(path = "/addProduct")
	public String showAddProductForm(Model model) {
		List<ProductLine> productLines = productLineService.getAllProductLines();

		// Pass the enum values to the view
		model.addAttribute("productSizes", ProductSizes.values());
		model.addAttribute("productLines", productLines);
		model.addAttribute("product", new Product());
		return "addProduct";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, Long productLineId) {
		ProductLine productLine = productLineService.getProductLineById(productLineId);
		product.setProductLine(productLine);
		productService.saveProduct(product);
		return "redirect:/all_products";
	}
}
