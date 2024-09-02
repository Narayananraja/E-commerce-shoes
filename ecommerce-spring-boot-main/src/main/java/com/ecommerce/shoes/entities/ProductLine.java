package com.ecommerce.shoes.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class ProductLine {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ProductCategory category;
	
	@Column(nullable=false)
	private String brand;
	
	@Column(nullable=false)
	private float price;
	
	@OneToMany(mappedBy="productLine" /*, fetch=FetchType.EAGER*/,cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	public ProductLine() {
		super();
	}

	public ProductLine(String name, ProductCategory category, String brand, float price) {
		super();
		this.name = name;
		this.category = category;
		this.brand = brand;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Product> getProducts() {
		return products;
	}

	@Override
	public String toString() {
		return "ProductLine [id=" + id + ", name=" + name + ", category=" + category + ", brand=" + brand + ", price="
				+ price + "]";
	}
	
}
