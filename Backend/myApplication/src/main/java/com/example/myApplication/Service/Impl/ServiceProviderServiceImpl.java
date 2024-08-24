package com.example.myApplication.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Repository.ServiceProviderRepository;
import com.example.myApplication.Service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	@Override
	public Optional<ServiceProvider> findServiceProviderByID(int id) {
		return serviceProviderRepository.findById(id);
	}

	@Override
	public List<ServiceProvider> getAllProviderAfterSearch(String keyword) {
		return serviceProviderRepository.searchByFirstNameOrLastNameIgnoreCase(keyword);
	}

	@Override
	public List<ServiceProvider> viewAllProvider() {
		return serviceProviderRepository.findAll();
	}
}