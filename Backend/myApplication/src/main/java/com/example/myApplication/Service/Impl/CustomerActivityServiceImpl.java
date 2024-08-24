package com.example.myApplication.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.CustomerActivity;
import com.example.myApplication.Entity.CustomerServiceStatus;
import com.example.myApplication.Repository.CustomerActivityRepository;
import com.example.myApplication.Repository.CustomerServiceStatusRepository;
import com.example.myApplication.Service.CustomerActivityService;

@Service
public class CustomerActivityServiceImpl implements CustomerActivityService {
	@Autowired
	private CustomerActivityRepository customerActivityRepository;
	@Autowired
	private CustomerServiceStatusRepository customerServiceStatusRepository;
	@Override
	public List<CustomerActivity> viewAllCustomerActivityByUserName(String username) {
		return customerActivityRepository.findByCustomer_UserName(username);
	}

	@Override
	public CustomerServiceStatus viewServiceStatus(String  username, int serviceId) {
		return customerServiceStatusRepository.findByCustomer_UserNameAndServices_Id(username,serviceId);
	}

	@Override
	public List<CustomerServiceStatus> viewAllCustomerHistory(String username) {
		return customerServiceStatusRepository.findAll();
	}

}