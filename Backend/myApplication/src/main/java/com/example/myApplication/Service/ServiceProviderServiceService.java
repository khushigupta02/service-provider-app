package com.example.myApplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Enum.Order;

public interface ServiceProviderServiceService {
	Optional<ServiceProviderService> findServiceProviderByID(int id);
	List<ServiceProviderService> viewAllServiceAndProviders();
	List<ServiceProviderService> getAllServiceAfterSearch(String keyword);
	List<ServiceProviderService> getAllServiceAfterSorting(Order order);
	Page<ServiceProviderService> viewAllServicePagination(Pageable pageable);
}