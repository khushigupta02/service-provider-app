package com.example.myApplication.Service;

import java.util.List;
import java.util.Optional;

import com.example.myApplication.Entity.ServiceType;
import com.example.myApplication.Entity.Services;

public interface ServiceService {
	Services addService(Services services );
	Optional<Services> viewServiceById(int id);
	ServiceType addServiceType(ServiceType serviceType);

	List<ServiceType> viewAllServiceType(ServiceType serviceType);
	List<Services> viewAllService(Services services);

	Services editService(Services services , int id);
	Services deleteService(int id);
	ServiceType editServiceType(ServiceType serviceType , int id);
	ServiceType deleteServiceType(int id);
	List<Services> getAllServiceAfterSearch(String keyword);
	List<Services> viewAllServiceByProvider(String username);
}