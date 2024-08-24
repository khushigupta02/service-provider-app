package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.ServiceType;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType , Integer> {
	ServiceType findByName(String name);
}