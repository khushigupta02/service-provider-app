package com.example.myApplication.Service;

import java.util.List;
import java.util.Optional;

import com.example.myApplication.Entity.ServiceProvider;

public interface ServiceProviderService {
	Optional<ServiceProvider> findServiceProviderByID(int id);
	List<ServiceProvider> getAllProviderAfterSearch (String keyword);
	List<ServiceProvider> viewAllProvider();
}