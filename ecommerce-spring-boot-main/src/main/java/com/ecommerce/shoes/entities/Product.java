package com.ecommerce.shoes.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Product {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ProductSizes size;
	
	@Column(nullable=false)
	private int inStockAmount;
	
	@ManyToOne
	private ProductLine productLine;
	
	@OneToMany(mappedBy="product",cascade = CascadeType.ALL)
	private List<ProductOrder> orders = new ArrayList<>();

	public Product() {
		super();
	}

	public Product(ProductSizes size, int inStockAmount, ProductLine productLine) {
		super();
		this.size = size;
		this.inStockAmount = inStockAmount;
		this.productLine = productLine;
	}

	public Long getId() {
		return id;
	}

	public ProductSizes getSize() {
		return size;
	}

	public void setSize(ProductSizes size) {
		this.size = size;
	}

	public int getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(int inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public ProductLine getProductLine() {
		return productLine;
	}

	public void setProductLine(ProductLine productLine) {
		this.productLine = productLine;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", size=" + size + ", inStockAmount=" + inStockAmount + ", productLine="
				+ productLine + "]";
	}
	
}
