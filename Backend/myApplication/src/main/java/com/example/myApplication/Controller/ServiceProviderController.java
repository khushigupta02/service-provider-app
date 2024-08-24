package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.ServiceProviderActivity;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Service.ServiceProviderActivityService;
import com.example.myApplication.Service.ServiceProviderService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceProviderController {
	@Autowired
	private ServiceProviderService serviceProviderService;
	@Autowired
	private ServiceProviderActivityService serviceProviderActivityService;

	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/findServiceProviderById/{id}")
	public Optional<ServiceProvider> getServiceProvider(@PathVariable int id){
		return serviceProviderService.findServiceProviderByID(id);
	}

	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/searchProvider/{keyword}")
	public List<ServiceProvider> getAllProviderAfterSearch(@PathVariable String keyword){
		return  serviceProviderService.getAllProviderAfterSearch(keyword);
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping("/viewAllProviderHistoryByUsername/{username}")
	public List<ServiceProviderActivity> viewAllProviderHistoryByUsername(@PathVariable String username){
		return serviceProviderActivityService.viewAllProviderActivity(username);
	}
	@GetMapping("/viewAllProvider")
	public List<ServiceProvider> viewAllProvider(){
		return serviceProviderService.viewAllProvider();
	}
}