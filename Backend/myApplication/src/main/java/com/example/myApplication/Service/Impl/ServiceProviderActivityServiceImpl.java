package com.example.myApplication.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.ServiceProviderActivity;
import com.example.myApplication.Repository.ServiceProviderActivityRepository;
import com.example.myApplication.Service.ServiceProviderActivityService;

@Service
public class ServiceProviderActivityServiceImpl implements ServiceProviderActivityService {
	@Autowired
	private ServiceProviderActivityRepository serviceProviderActivityRepository;
	@Override
	public List<ServiceProviderActivity> viewAllProviderActivity(String username) {
		return serviceProviderActivityRepository.findAll();
	}
}