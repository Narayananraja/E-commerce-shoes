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
	
}
