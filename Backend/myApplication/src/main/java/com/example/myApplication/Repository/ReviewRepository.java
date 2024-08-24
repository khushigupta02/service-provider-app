package com.example.myApplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.Review;
import com.example.myApplication.Entity.ServiceProviderService;

public interface ReviewRepository extends JpaRepository<Review , Integer> {
	Optional<Review> findByCustomerAndServiceProviderService(Customer customer , ServiceProviderService serviceProviderService);
	Review findByCustomer_IdAndServiceProviderService_Id(int customerId, int serviceProviderServiceId);
	List<Review> findByServiceProviderService_ServiceProvider_UserName(String username);
	List<Review> findByCustomer_UserName(String username);
	List<Review> findByAndServiceProviderService_Service_Id(int serviceId);
}