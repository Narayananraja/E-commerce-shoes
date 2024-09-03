package com.ecommerce.shoes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="contact")
	private User user;
	
	private String phoneNumber;
	private String email;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
}
