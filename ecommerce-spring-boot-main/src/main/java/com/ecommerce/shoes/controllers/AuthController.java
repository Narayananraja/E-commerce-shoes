package com.ecommerce.shoes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.shoes.entities.Contact;
import com.ecommerce.shoes.entities.User;
import com.ecommerce.shoes.services.AuthService;

@Controller
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;
	
	@GetMapping(path="/adminHome")
	public String showAdminHome() {
		return "adminHome";
	}
	
	@GetMapping(path="/changePassword")
	public String showChangePassword() {
		return "changePassword";
	}
	
	@PostMapping(path="/register_user", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public String register(@RequestParam MultiValueMap<String, String> userInfo, Model model) {
		
		if (userInfo.get("username").get(0).length() < 1 || userInfo.get("password").get(0).length() < 1) {
			model.addAttribute("message", "Register failed");
	        return "userError";
		}
		
		Map<String, String> registeredUserDetails = userInfo.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
		
		Contact contact = new Contact();
		
		contact.setPhoneNumber(registeredUserDetails.get("phoneNumber"));
		contact.setEmail(registeredUserDetails.get("email"));
		contact.setLine1(registeredUserDetails.get("line1"));
		contact.setLine2(registeredUserDetails.get("line2"));
		contact.setCity(registeredUserDetails.get("city"));
		contact.setState(registeredUserDetails.get("state"));
		contact.setPostalCode(registeredUserDetails.get("postalCode"));
		contact.setCountry(registeredUserDetails.get("country"));
		
		LOGGER.info("Entered contact -> {}", contact);
		
		User user = new User();
		
		user.setUsername(registeredUserDetails.get("username"));
		user.setPassword(registeredUserDetails.get("password"));
		user.setFirstName(registeredUserDetails.get("firstName"));
		user.setLastName(registeredUserDetails.get("lastName"));
		user.setContact(contact);
		
		LOGGER.info("Entered user -> {}", user);

		User registeredUser = authService.register(user);
		
		if (null != registeredUser) {
			model.addAttribute("user", registeredUser);
			model.addAttribute("username", registeredUser.getUsername());
			return "shopHome";
		}
		
		model.addAttribute("message", "Register failed");
        return "userError";
    }
	
	@PostMapping(path="/login", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String login(@RequestParam MultiValueMap<String, String> userCredentials, Model model) {
		
		if (userCredentials.get("username").get(0).length() < 1) {
			model.addAttribute("message", "Login failed");
	        return "userError";
		}
		
		User user = new User();
		user.setUsername(userCredentials.get("username").get(0));
		user.setPassword(userCredentials.get("password").get(0));
		
		User loggedInUser = authService.login(user);
		LOGGER.info("Logged in user -> {}", loggedInUser);

		if (null != loggedInUser) {
			
			model.addAttribute("username", loggedInUser.getUsername());

			if (loggedInUser.getUsername().equals("admin")) {
				return "adminHome"; 
			}
			
			return "shopHome"; 
		}
		
		model.addAttribute("message", "Login failed");
        return "userError";
	}
	
	@PostMapping(path="/change_password", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String changePassword(@RequestParam MultiValueMap<String, String> userCredentials, Model model) {
		
		User user = new User();
		user.setUsername("admin");
		user.setPassword(userCredentials.get("password").get(0));
		
		User adminUser = authService.changePassword(user);
		LOGGER.info("Logged in user -> {}", adminUser);

		if (null != adminUser) {
			model.addAttribute("username", adminUser.getUsername());
			return "adminHome"; 
		}
		
		model.addAttribute("message", "Changing password failed");
        return "adminError";
	}
	
	@GetMapping(path="/all_users")
	public String showAllUsers(Model model) {
				
		List<User> allUsers = authService.getAllUsers();
		LOGGER.info("Found users -> {}", allUsers);
		
		if (null != allUsers) {
			model.addAttribute("users", allUsers);
			return "usersList";
		}

		model.addAttribute("message", "Loading product failed");
        return "adminError";
	}

	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam Long id){
		authService.deleteUser(id);
		return "redirect:/all_users";
	}
	
	@PostMapping(path="/search_username", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String getUserByUsername(@RequestParam MultiValueMap<String, String> userCredentials, Model model) {
		
		if (null == userCredentials.get("username").get(0)) {
			model.addAttribute("message", "User search failed");
	        return "adminError";
		}
		
		User user = new User();
		user.setUsername(userCredentials.get("username").get(0));
		
		User searchedUser = authService.getUserByUsername(user);
		LOGGER.info("Found user -> {}", searchedUser);
		
		List<User> userArr = new ArrayList<>();
		userArr.add(searchedUser);

		if (null != searchedUser) {
			model.addAttribute("users", userArr);
			return "usersList"; 
		}
		
		model.addAttribute("message", "User search failed");
        return "adminError";
	}

	// Show add/edit user form
	@GetMapping("/addUser")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new User()); // Provide a new User object for adding
		model.addAttribute("title", "Add New User");
		model.addAttribute("actionText", "Add User");
		return "editUser";
	}

	@GetMapping(path = "/editUser")
	public String showEditUserForm(@RequestParam("id") Long id, Model model) {
		User user = authService.findById(id);
		if (user == null) {
			return "redirect:/userList?error";
		}
		model.addAttribute("user", user);
		model.addAttribute("title", "Edit User");
		model.addAttribute("actionText", "Update User");
		return "editUser";
	}

	@PostMapping(path = "/saveUser")
	public String saveUser(@RequestParam("id") Long id,
						   @RequestParam("username") String username,
						   @RequestParam("password") String password,
						   @RequestParam("firstName") String firstName,
						   @RequestParam("lastName") String lastName) {
		User user = (id != null && id > 0) ? authService.findById(id) : new User();
		if (user == null) {
			return "redirect:/all_users?error";
		}
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		authService.saveUser(user);
		return "redirect:/all_users";
	}

}
