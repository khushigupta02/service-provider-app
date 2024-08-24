package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.CustomerActivity;
import com.example.myApplication.Entity.CustomerServiceStatus;
import com.example.myApplication.Service.CustomerActivityService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CustomerActivityController {
	@Autowired
	private CustomerActivityService customerActivityService;


	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewAllCustomerActivityByUsername/{username}")
	public List<CustomerActivity> viewAllCustomerActivityByUsername (@PathVariable String username){
		return customerActivityService.viewAllCustomerActivityByUserName(username);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewServiceStatus/{username}/{serviceId}")
	public CustomerServiceStatus viewServiceStatus(@PathVariable String username , @PathVariable int serviceId){
		return customerActivityService.viewServiceStatus(username ,serviceId);
	}

	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewAllCustomerHistory/{username}")
	public List<CustomerServiceStatus> viewAllCustomerHistory(@PathVariable String username){
		return customerActivityService.viewAllCustomerHistory(username);
	}
}