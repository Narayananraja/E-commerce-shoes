package com.ecommerce.shoes.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.shoes.entities.Contact;
import com.ecommerce.shoes.entities.User;
import com.ecommerce.shoes.repositories.ContactRepository;
import com.ecommerce.shoes.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;

@Component
public class AuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	public User login(User user) {
		
		LOGGER.info("Given user -> {}", user);
		/*
		List<User> foundUsers = userRepository.findAll();
		LOGGER.info("Found users -> {}", foundUsers.size());
		LOGGER.info("Found username -> {}", foundUsers.get(0).getUsername());
		LOGGER.info("Found password -> {}", foundUsers.get(0).getPassword());
		LOGGER.info("Found user -> {}", foundUsers.get(0));
		LOGGER.info("Found contact -> {}", foundUsers.get(0).getContact());
		*/
		
		User foundUser = userRepository.findByUsername(user.getUsername());
		LOGGER.info("Found user -> {}", foundUser);
		LOGGER.info("Found contact -> {}", foundUser.getContact());

		LOGGER.info("Given username -> {}", user.getUsername());
		LOGGER.info("Found username -> {}", foundUser.getUsername());
		LOGGER.info("Found password -> {}", foundUser.getPassword());
		LOGGER.info("Found password -> {}", foundUser.getPassword());

		if (!foundUser.getPassword().equals(user.getPassword())) {
			return null;
		}
				
		return foundUser;
	}
	
	public User register(User user) {
		
		Contact contact = contactRepository.save(user.getContact());
		LOGGER.info("Saved contact -> {}", contact);

		User savedUser = userRepository.save(user);
		LOGGER.info("Saved user -> {}", savedUser);
		
		return savedUser;
	}
	
	public User changePassword(User user) {
		
		LOGGER.info("Given user -> {}", user);
		
		User foundUser = userRepository.findByUsername(user.getUsername());
		LOGGER.info("Found user -> {}", foundUser);
		
		foundUser.setPassword(user.getPassword());
		User savedUser = userRepository.save(foundUser);
		LOGGER.info("Saved user -> {}", savedUser);

		return savedUser;
	}
	
	public List<User> getAllUsers() {
				
		List<User> allUsers = userRepository.findAll();
		LOGGER.info("Found users -> {}", allUsers);
		
		return allUsers;
	}
	
	public User getUserByUsername(User user) {
		User foundUser = userRepository.findByUsername(user.getUsername());
		return foundUser;
	}

    public void deleteUser(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("User not found with id: " + id);
		}
    }

	public User findById(Long id) {
		return userRepository.getById(id);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}
}
