package com.ecommerce.shoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.shoes.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);	

}
