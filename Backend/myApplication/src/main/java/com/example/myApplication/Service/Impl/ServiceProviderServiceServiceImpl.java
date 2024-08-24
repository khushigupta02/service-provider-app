package com.example.myApplication.Service.Impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Enum.Order;
import com.example.myApplication.Repository.ServiceProviderServiceRepository;
import com.example.myApplication.Service.ServiceProviderServiceService;
import com.example.myApplication.Sorting.ServiceProviderServiceHourlyRateComparator;

@Service
public class ServiceProviderServiceServiceImpl implements ServiceProviderServiceService {
	@Autowired
	private ServiceProviderServiceRepository serviceProviderServiceRepository;
	@Override
	public Optional<ServiceProviderService> findServiceProviderByID(int id) {

		var serviceProviderService = serviceProviderServiceRepository.findById(id);
		return serviceProviderService;
	}

	@Override
	public List<ServiceProviderService> viewAllServiceAndProviders() {
		return serviceProviderServiceRepository.findAll();
	}

	@Override
	public List<ServiceProviderService> getAllServiceAfterSearch(String keyword) {
		return serviceProviderServiceRepository.findByService_ServiceNameContainingIgnoreCaseOrServiceProvider_FirstNameContainingIgnoreCaseOrServiceProvider_LastNameContainingIgnoreCase(keyword);
	}

	@Override
	public List<ServiceProviderService> getAllServiceAfterSorting(Order order) {
		List<ServiceProviderService> services = serviceProviderServiceRepository.findAll();
		var serviceProviderServiceArrayList = new ArrayList<>(services);

		// Create a comparator with the specified order
		Comparator<ServiceProviderService> comparator = new ServiceProviderServiceHourlyRateComparator(order);

		// Sort the list using the comparator
		serviceProviderServiceArrayList.sort(comparator);

		return serviceProviderServiceArrayList;
	}

	@Override
	public Page<ServiceProviderService> viewAllServicePagination(Pageable pageable) {
		return serviceProviderServiceRepository.findAll(pageable);
	}

}