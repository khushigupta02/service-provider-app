package com.example.myApplication.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.BookService;
import com.example.myApplication.Entity.BookedServiceStatus;
import com.example.myApplication.Service.BookingService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BookServiceController {

	@Autowired
	private BookingService bookingService;

	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping("/bookService/{id}")
	public BookService bookService(@PathVariable int id){
		return bookingService.createBooking(id);
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping("/viewAllBooking")
	public List<BookService> viewAllBookService(){
		return bookingService.viewAllBooking();
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@PostMapping("/updateBookedServiceStatus/{id}")
	public BookedServiceStatus updateBookedService(@RequestBody BookedServiceStatus bookedServiceStatus , @PathVariable int id){
		return bookingService.updateBookedServiceStatus(bookedServiceStatus,id);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping ("/cancelBookedService/{id}")
	public BookService cancelBookService( @PathVariable int id){
		return bookingService.cancelBooking(id);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping("/reBookService/{id}")
	public BookService reBookService(@PathVariable int id){
		return bookingService.reBookService(id);
	}
	
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewBookedServiceStatus/{username}/{serviceId}")
	public String viewBookedServiceStatus(@PathVariable String username , @PathVariable int serviceId){
		return  bookingService.viewBookedServiceStatus(username, serviceId);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/getBookingId/{username}/{serviceId}")
	public Optional<Integer> getBookingId(@PathVariable String username , @PathVariable int serviceId){
		return bookingService.getBookingId(username,serviceId);
	}

	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping ("/viewBookingByServiceProviderUsername/{userName}")
	public List<BookService> viewBookingByServiceProviderUsername(@PathVariable String userName){
		return bookingService.viewBookingByServiceProviderUsername(userName);
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping ("/viewBookingDetailByBookingId/{bookingId}")
	public Optional<BookService> viewBookingDetailByBookingId(@PathVariable int bookingId){
		return bookingService.viewBookingDetailByBookingId(bookingId);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping ("/viewAllBookingDetailsByUsername/{username}")
	public List<BookService> viewAllBookingDetailByUsername(@PathVariable String username){
		return bookingService.viewAllBookingDetailsByUsername(username);
	}
}