package com.example.myApplication.Service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.User;
import com.example.myApplication.Repository.CustomerRepository;
import com.example.myApplication.Repository.RoleRepository;
import com.example.myApplication.Repository.UserRepository;
import com.example.myApplication.Service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public User addCustomer(User user) {
		var customerTypeReq = user.getUserType().getRoleName();
		var customerType = roleRepository.findByRoleName(customerTypeReq);
		var customerExist = userRepository.findByUserNameAndEmail(user.getUserName() , user.getEmail());
		if(customerExist != null){
			throw new RuntimeException("Try another Username and Password");
		}
		if(customerType!=null){
			user.setUserType(customerType);
			return userRepository.save(user);
		}
		return userRepository.save(user);
	}
	@Override
	public List<User> viewAllCustomer(String userType) {
		return  userRepository.findByUserType_RoleName(userType);
	}

	@Override
	public List<Customer> viewAllCustomers() {
		return customerRepository.findAll();
	}

}