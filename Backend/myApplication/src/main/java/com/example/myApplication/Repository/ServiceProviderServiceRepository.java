package com.example.myApplication.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.ServiceProviderService;

@Repository
public interface ServiceProviderServiceRepository extends JpaRepository<ServiceProviderService,Integer> {
	@Query("SELECT s FROM ServiceProviderService s " +
			"JOIN s.serviceProvider sp " +
			"JOIN s.service se " +
			"WHERE LOWER(se.serviceName) LIKE %:keyword% " +
			"OR LOWER(sp.firstName) LIKE %:keyword% " +
			"OR LOWER(sp.lastName) LIKE %:keyword%")
	List<ServiceProviderService> findByService_ServiceNameContainingIgnoreCaseOrServiceProvider_FirstNameContainingIgnoreCaseOrServiceProvider_LastNameContainingIgnoreCase(String keyword);

	Page<ServiceProviderService> findAll(Pageable pageable);
}