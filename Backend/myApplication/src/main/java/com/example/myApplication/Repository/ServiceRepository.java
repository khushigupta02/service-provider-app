package com.example.myApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.ServiceType;
import com.example.myApplication.Entity.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {
	Services findByServiceName(String serviceName);
	List<Services> findByServiceNameContainingIgnoreCase(String keyword);
	List<Services> findByServiceProvider_UserName(String username);
}