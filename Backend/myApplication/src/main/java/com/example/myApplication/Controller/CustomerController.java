package com.example.myApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.User;
import com.example.myApplication.Service.CustomerService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@PostMapping("/addCustomer")
	public User addCustomer(@RequestBody User user){
		return customerService.addCustomer(user);
	}
	@GetMapping("/viewAllUsers/{userType}")
	public List<User> viewAllCustomer(@PathVariable String userType){
		return customerService.viewAllCustomer(userType);
	}
	@GetMapping("/viewAllCustomer" )
	public List<Customer> viewAllCustomer(){
		return customerService.viewAllCustomers();
	}

}