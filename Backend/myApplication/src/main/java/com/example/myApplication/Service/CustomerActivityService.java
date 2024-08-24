package com.example.myApplication.Service;

import java.util.List;

import com.example.myApplication.Entity.CustomerActivity;
import com.example.myApplication.Entity.CustomerServiceStatus;

public interface CustomerActivityService {
	List<CustomerActivity> viewAllCustomerActivityByUserName(String username);
	CustomerServiceStatus viewServiceStatus( String username , int serviceId);
	List<CustomerServiceStatus> viewAllCustomerHistory(String viewAllCustomerHistory);
}