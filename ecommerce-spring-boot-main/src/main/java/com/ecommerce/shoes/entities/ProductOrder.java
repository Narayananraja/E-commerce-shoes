package com.ecommerce.shoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@Entity
public class ProductOrder {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private int amount;
	
	@Column(nullable=false)
	private float price;
	
	@Column(nullable=false)
	private float subtotal;
	
	@Column(nullable=false)
	private float shipping;
	
	@Column(nullable=false)
	private float tax;
	
	@Column(nullable=false)
	private float total;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="productOrder",cascade = CascadeType.ALL)
	private OrderPayment payment;

	public ProductOrder(int amount, float price, float subtotal, float shipping, float tax, float total, Product product) {
		super();
		this.amount = amount;
		this.price = price;
		this.subtotal = subtotal;
		this.shipping = shipping;
		this.tax = tax;
		this.total = total;
		this.product = product;
	}
}
