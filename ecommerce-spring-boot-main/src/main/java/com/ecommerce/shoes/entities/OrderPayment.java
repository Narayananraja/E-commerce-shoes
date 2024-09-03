package com.ecommerce.shoes.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
@Data
@NoArgsConstructor
@Entity
public class OrderPayment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private ProductOrder productOrder;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(nullable=false)
	private float total;

	public OrderPayment(ProductOrder productOrder, float total) {
		super();
		this.productOrder = productOrder;
		this.total = total;
	}
}
