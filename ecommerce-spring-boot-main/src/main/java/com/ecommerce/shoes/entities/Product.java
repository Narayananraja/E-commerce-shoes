package com.ecommerce.shoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
}
