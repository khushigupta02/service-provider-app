package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.ServiceType;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Service.ServiceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceController {
	@Autowired
	private ServiceService serviceService;

	@PreAuthorize("hasAuthority('Service Provider')")
	@PostMapping("/addService")
	public Services addService(@RequestBody Services services ){
		return serviceService.addService(services );
	}
	@PostMapping("/addServiceType")
	public ServiceType addServiceType(@RequestBody ServiceType serviceType){
		return serviceService.addServiceType(serviceType);
	}

	@GetMapping("/viewAllServiceType")
	public List<ServiceType> viewAll(ServiceType serviceType){
		return serviceService.viewAllServiceType(serviceType);
	}
//	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewAllService")
//	@GetMapping(value = "/viewAllService", produces = { MediaType.APPLICATION_XML_VALUE})

	public List<Services> viewAllService(Services services){
		return serviceService.viewAllService(services);
	}
	@GetMapping("/viewAllServiceByServiceProviderUsername/{username}")
	public List<Services> viewAllServiceByProvider(@PathVariable String username){
		return serviceService.viewAllServiceByProvider(username);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewServiceByID/{id}")
	public Optional<Services> viewServiceByID(@PathVariable int id){
		return serviceService.viewServiceById(id);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/searchService/{keyword}")
	public List<Services> getAllServiceAfterSearch(@PathVariable String keyword){
		return  serviceService.getAllServiceAfterSearch(keyword);
	}

	@PutMapping("/editService/{id}")
	public Services editService(@RequestBody Services services , @PathVariable int id){
		return serviceService.editService(services, id);
	}

	@PreAuthorize("hasAuthority('Service Provider')")
	@DeleteMapping("/deleteService/{id}")
	public Services deleteService(@PathVariable int id){
		return serviceService.deleteService(id);
	}
	@DeleteMapping("/deleteServiceType/{id}")
	public Services deleteServiceType(@PathVariable int id){
		return serviceService.deleteService(id);
	}
	@PutMapping("/editServiceType/{id}")
	public ServiceType editServiceType(@RequestBody ServiceType serviceType , @PathVariable int id){
		return serviceService.editServiceType(serviceType , id);
	}

}