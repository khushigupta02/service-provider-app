package com.example.myApplication.Repository;

import java.awt.print.Book;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.BookService;
import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.CustomerActivity;
import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.ServiceProviderService;

@Repository
public interface BookServiceRepository extends JpaRepository<BookService, Integer> {
	List<BookService> findByServiceProviderService_ServiceProvider_UserName(String username);
	BookService findByCustomer_UserNameAndServiceProviderService_ServiceId(String username , int serviceId);
	@Query("SELECT bs.status FROM BookService bs WHERE bs.customer.userName = :username AND bs.serviceProviderService.service.id = :serviceId")
	String findStatusByCustomerUserNameAndServiceProviderService_ServiceId(@Param("username") String username, @Param("serviceId") int serviceId);
	int findByCustomerAndServiceProviderService_ServiceId(Customer customer , int id);
	BookService findByCustomerAndServiceProviderService(Customer customer, ServiceProviderService serviceProviderService);
	List<BookService> findByCustomer_UserName(String username);
}