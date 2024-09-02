package com.ecommerce.shoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shoes.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	

}
