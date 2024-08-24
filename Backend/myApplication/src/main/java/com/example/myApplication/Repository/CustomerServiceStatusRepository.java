package com.example.myApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.CustomerServiceStatus;
import com.example.myApplication.Entity.Services;

public interface CustomerServiceStatusRepository extends JpaRepository<CustomerServiceStatus , Integer> {
CustomerServiceStatus findByCustomer_UserNameAndServices_Id(String username, int serviceId);

	CustomerServiceStatus findByCustomerAndServices(Customer customer, Services service);

}