package com.example.myApplication.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Entity.ServiceType;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Repository.ServiceProviderRepository;
import com.example.myApplication.Repository.ServiceProviderServiceRepository;
import com.example.myApplication.Repository.ServiceRepository;
import com.example.myApplication.Repository.ServiceTypeRepository;
import com.example.myApplication.Repository.UserRepository;
import com.example.myApplication.Service.ServiceService;
@Service
public class ServiceServiceImpl implements ServiceService {
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	@Autowired
	private ServiceProviderServiceRepository serviceProviderServiceRepository;
	@Override
	public Services addService(Services services) {
		var serviceTypeReq = services.getServiceType().getName();
		var serviceName = serviceRepository.findByServiceName(services.getServiceName());


			var newService = new Services();
			newService.setServiceName(services.getServiceName());
			newService.setDescription(services.getDescription());
			newService.setHourlyRate(services.getHourlyRate());

			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDetails.getUsername();

			// Assuming the username is the service provider's username
			ServiceProvider serviceProvider = serviceProviderRepository.findByUserName(username);
			if (serviceProvider == null) {
				throw new RuntimeException("Service Provider not found");
			}

			newService.setServiceProvider(serviceProvider);

			// Check if the service type exists
			var serviceDbType = serviceTypeRepository.findByName(serviceTypeReq);
			if (serviceDbType != null) {
				newService.setServiceType(serviceDbType);
			} else {
				var newServiceType = new ServiceType();
				newServiceType.setName(serviceTypeReq);
				newService.setServiceType(serviceTypeRepository.save(newServiceType));
			}

			newService = serviceRepository.save(newService);

			// Create a new ServiceProviderService instance
			ServiceProviderService serviceProviderService = new ServiceProviderService();
			serviceProviderService.setServiceProvider(serviceProvider);
			serviceProviderService.setService(newService);

			// Save the ServiceProviderService entity
			serviceProviderServiceRepository.save(serviceProviderService);

			return newService;

	}

	@Override
	public Optional<Services> viewServiceById(int id) {
		return serviceRepository.findById(id);
	}

	@Override
	public ServiceType addServiceType(ServiceType serviceType) {
		var serviceTypeDB = serviceTypeRepository.findByName(serviceType.getName());
		if (serviceTypeDB!=null){
			throw new RuntimeException("This Service Type  Already Exixts");
		}
		return serviceTypeRepository.save(serviceType);
	}

	@Override
	public List<ServiceType> viewAllServiceType(ServiceType serviceType) {
		return serviceTypeRepository.findAll();
	}

	@Override
	public List<Services> viewAllService(Services services) {
		return serviceRepository.findAll();
	}

	@Override
	public Services editService(Services services, int id) {
		var dbService = serviceRepository.findById(id);
		if(dbService.isPresent()){
			var existingService = dbService.get();
			existingService.setServiceName(services.getServiceName());
			existingService.setServiceType(services.getServiceType());
			existingService.setDescription(services.getServiceName());
			existingService.setHourlyRate(services.getHourlyRate());
			return serviceRepository.save(existingService);
		}
		return null;
	}
	@Override
	public Services deleteService(int id) {
		var dbService = serviceRepository.findById(id);
		if(dbService.isPresent()){
			var existingService = dbService.get();
			 serviceRepository.delete(existingService);
		}
		return null;
	}

	@Override
	public ServiceType editServiceType(ServiceType serviceType, int id) {
		var dbServiceType = serviceTypeRepository.findById(id);
		if(dbServiceType.isPresent()){
			var existingServiceType = dbServiceType.get();
			existingServiceType.setName(serviceType.getName());
			return serviceTypeRepository.save(existingServiceType);
		}
		return null;
	}
	@Override
	public ServiceType deleteServiceType(int id) {
		var dbServiceType = serviceTypeRepository.findById(id);
		if(dbServiceType.isPresent()){
			var existingServiceType = dbServiceType.get();
			serviceTypeRepository.delete(existingServiceType);		}
		return null;
	}

	@Override
	public List<Services> getAllServiceAfterSearch(String keyword) {
		return serviceRepository.findByServiceNameContainingIgnoreCase(keyword);
	}

	@Override
	public List<Services> viewAllServiceByProvider(String username) {
		return serviceRepository.findByServiceProvider_UserName(username);
	}

}