package com.example.myApplication.Service;

import java.util.List;

import com.example.myApplication.Entity.ServiceProviderActivity;

public interface ServiceProviderActivityService {
	List<ServiceProviderActivity> viewAllProviderActivity(String username);
}