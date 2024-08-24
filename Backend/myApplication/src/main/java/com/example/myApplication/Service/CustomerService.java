package com.example.myApplication.Service;

import java.util.List;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.User;

public interface CustomerService {
	User addCustomer(User user);
	List<User> viewAllCustomer(String userType);
	List<Customer> viewAllCustomers();

}