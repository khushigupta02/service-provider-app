package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Enum.Order;
import com.example.myApplication.Repository.ServiceProviderServiceRepository;
import com.example.myApplication.Service.ServiceProviderServiceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ServiceProviderServiceController {
	@Autowired
	private ServiceProviderServiceRepository serviceProviderServiceRepository;
	@Autowired
	private ServiceProviderServiceService serviceProviderService;

	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/findServiceByServiceProviderByID/{id}")
	public Optional<ServiceProviderService> findService(@PathVariable int id){
		return serviceProviderService.findServiceProviderByID(id);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewAllServiceAndProvider")
	public List<ServiceProviderService> viewAllServiceAndProviders(){
		return serviceProviderService.viewAllServiceAndProviders();
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/searchAllService/{keyword}")
	public List<ServiceProviderService> getAllServiceAfterSearch(@PathVariable String keyword){
		return  serviceProviderService.getAllServiceAfterSearch(keyword);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/sortAllService/{order}")
	public List<ServiceProviderService> getAllServiceAfterSorting(@PathVariable Order order){
		return  serviceProviderService.getAllServiceAfterSorting(order);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewAllServicePagination/{page}/{size}")
	public Page<ServiceProviderService> viewAllServicePagination(@PathVariable int page , @PathVariable int size){
		Pageable pageable = PageRequest.of(page,size);
		return  serviceProviderService.viewAllServicePagination(pageable);
	}

}