package com.example.myApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Integer> {
	Customer findByUserName(String userName);
	@Query("SELECT id FROM Customer c WHERE c.userName = ?1")
	Customer findIdByUserName(String userName);

}