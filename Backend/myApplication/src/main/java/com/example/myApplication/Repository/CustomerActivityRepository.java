package com.example.myApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.CustomerActivity;

public interface CustomerActivityRepository extends JpaRepository<CustomerActivity,Integer> {
	List<CustomerActivity> findByCustomer_UserName(String userName);

}