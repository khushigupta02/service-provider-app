package com.example.myApplication.Service;

import java.util.List;
import java.util.Optional;

import com.example.myApplication.Entity.BookService;
import com.example.myApplication.Entity.BookedServiceStatus;

public interface BookingService {
	BookService createBooking(int id) ;
	BookService reBookService(int id);
	BookedServiceStatus updateBookedServiceStatus(BookedServiceStatus bookedServiceStatus,int id);
	BookService cancelBooking(int id);
	String viewBookedServiceStatus(String username , int serviceId);
	List<BookService> viewBookingByServiceProviderUsername(String username);
	Optional<Integer> getBookingId(String username , int serviceId);
	List<BookService> viewAllBookingDetailsByUsername(String username);
	Optional<BookService> viewBookingDetailByBookingId(int bookingId);
	List<BookService> viewAllBooking();

}