package com.example.myApplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.User;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider ,Integer> {
	ServiceProvider findByUserName(String userName);
	@Query("SELECT sp FROM ServiceProvider sp WHERE LOWER(sp.firstName) LIKE %:keyword% OR LOWER(sp.lastName) LIKE %:keyword%")
	List<ServiceProvider> searchByFirstNameOrLastNameIgnoreCase(String keyword);
}