package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.myApplication.Entity.User;
import com.example.myApplication.Service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User addUser(@RequestBody User user){
		return userService.addUser(user);
	}

//	@PostMapping("/userLogin")
//	public User userLogin(@RequestBody User user){
//		return userService.userLogin(user);
//	}

	@GetMapping("/customerDashboard")
	@PreAuthorize("hasAuthority('Customer')")
	public String customerDashboard() {
		return "Welcome to customer Profile";
	}
	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		return ResponseEntity.ok().build();
	}

	@GetMapping("/serviceProviderDashboard")
	@PreAuthorize("hasAuthority('Service Provider')")
	public String serviceProviderDashboard() {
		return "Welcome to service provider Profile";
	}
	@GetMapping("/adminDashboard")
	@PreAuthorize("hasAuthority('Admin')")
	public String adminDashboard() {
		return "Welcome to Admin Dashboard";
	}

//	@PostMapping("/logout")
//	public String userLogout(HttpServletRequest request) {
//		return "redirect:/login?logout";
//	}

	@GetMapping("/viewAllUser")
	public List<User> viewAllUser(User user){
		return userService.viewAll(user);
	}
	@GetMapping("/findByUsername/{userName}")
	public Optional<User> findByUserName(@PathVariable String userName) {
		return userService.findByUserName(userName);
	}
	@PutMapping("/editUser/{id}")
	public User editUser(@RequestBody User user, @PathVariable int id) {
		return userService.editUser(user, id );
	}

	@DeleteMapping("/deleteUser/{id}")
	public User deleteUser(@PathVariable int id){
		return userService.deleteUser(id);
	}
}