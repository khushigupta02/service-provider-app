package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.ServiceProviderActivity;

public interface ServiceProviderActivityRepository extends JpaRepository<ServiceProviderActivity , Integer> {
}