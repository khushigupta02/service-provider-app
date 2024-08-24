package com.example.myApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.User;
import com.example.myApplication.Repository.UserRepository; // Import UserRepository

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DashboardController {

	@Autowired
	private UserRepository userRepository; // Inject UserRepository

//	@GetMapping("/customerDashboard/{username}")
//	public User customerDashboard(@PathVariable("username") String userName){
//		return userRepository.findByUserName(userName); // Fetch user details directly from UserRepository
//	}
}