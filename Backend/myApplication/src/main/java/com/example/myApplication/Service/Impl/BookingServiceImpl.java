package com.example.myApplication.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.BookService;
import com.example.myApplication.Entity.BookedServiceStatus;
import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.CustomerActivity;
import com.example.myApplication.Entity.CustomerServiceStatus;
import com.example.myApplication.Entity.ServiceProviderActivity;
import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Enum.Message;
import com.example.myApplication.Enum.Status;
import com.example.myApplication.Repository.BookedServiceStatusRepository;
import com.example.myApplication.Repository.BookServiceRepository;
import com.example.myApplication.Repository.CustomerActivityRepository;
import com.example.myApplication.Repository.CustomerRepository;
import com.example.myApplication.Repository.CustomerServiceStatusRepository;
import com.example.myApplication.Repository.ServiceProviderActivityRepository;
import com.example.myApplication.Repository.ServiceProviderRepository;
import com.example.myApplication.Repository.ServiceProviderServiceRepository;
import com.example.myApplication.Service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookServiceRepository bookServiceRepository;
	@Autowired
	private ServiceProviderServiceRepository serviceProviderServiceRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private BookedServiceStatusRepository bookedServiceStatusRepository;
	@Autowired
	private CustomerActivityRepository customerActivityRepository;
	@Autowired
	private CustomerServiceStatusRepository customerServiceStatusRepository;
	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	@Autowired
	private ServiceProviderActivityRepository serviceProviderActivityRepository;

	@Override
	public BookService createBooking(int id) {
		Optional<ServiceProviderService> serviceProviderServiceOptional = serviceProviderServiceRepository.findById(id);
		var authentication = SecurityContextHolder.getContext()
												  .getAuthentication();
		var username = authentication.getName(); // Get username from authentication
		var serviceId = serviceProviderServiceOptional.get().getService();

		if (serviceProviderServiceOptional.isPresent()) {
			ServiceProviderService serviceProviderService = serviceProviderServiceOptional.get();
			Customer customer = customerRepository.findByUserName(username);
			if (customer == null) {
				throw new IllegalStateException("Customer not found for the logged-in user.");
			}

			BookService bookService = new BookService();
			bookService.setServiceProviderService(serviceProviderService);
			bookService.setCustomer(customer);
			bookService.setStatus(Status.REQUEST_SENT);
			bookService.setComments(Message.SERVICE_BOOKING_REQUEST_SENT.getMessage());
			bookService.setBookedAt(new Date());

			CustomerServiceStatus customerServiceStatus = new CustomerServiceStatus();
			customerServiceStatus.setCustomer(customer);
			customerServiceStatus.setStatus(Status.REQUEST_SENT);
			customerServiceStatus.setServices(serviceId);
			customerServiceStatusRepository.save(customerServiceStatus);

			ServiceProviderActivity serviceProviderActivity = new ServiceProviderActivity();
			serviceProviderActivity.setStatus(Status.REQUEST_SENT);
			serviceProviderActivity.setServices(serviceId);
			serviceProviderActivity.setServiceProvider(serviceProviderServiceOptional.get().getServiceProvider());
			serviceProviderActivity.setUpdatedAt(new Date());
			serviceProviderActivity.setCustomer(customer);
			serviceProviderActivityRepository.save(serviceProviderActivity);

			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setBookedService(bookService);
			customerActivity.setCustomer(customer);
			customerActivityRepository.save(customerActivity);

			var serviceProviderId = serviceProviderServiceOptional.get().getServiceProvider();
			BookedServiceStatus bookedServiceStatus = new BookedServiceStatus();
			bookedServiceStatus.setBookService(bookService);
			bookedServiceStatus.setServiceProvider(serviceProviderId);
			bookedServiceStatus.setComments(String.valueOf(Message.SERVICE_BOOKING_REQUEST_SENT.getMessage()));
			bookedServiceStatus.setStatus(Status.REQUEST_SENT);
			bookedServiceStatusRepository.save(bookedServiceStatus);
			return bookServiceRepository.save(bookService);
		} else {
			throw new IllegalArgumentException("ServiceProviderService with id " + id + " not found.");
		}
	}

	@Override
	public BookService reBookService(int serviceProviderServiceId) {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var username = authentication.getName();

		// Find the customer
		Customer customer = customerRepository.findByUserName(username);
		if (customer == null) {
			throw new IllegalStateException("Customer not found for the logged-in user.");
		}

		// Find the ServiceProviderService
		Optional<ServiceProviderService> serviceProviderServiceOptional = serviceProviderServiceRepository.findById(serviceProviderServiceId);
		if (!serviceProviderServiceOptional.isPresent()) {
			throw new IllegalArgumentException("ServiceProviderService with id " + serviceProviderServiceId + " not found.");
		}
		ServiceProviderService serviceProviderService = serviceProviderServiceOptional.get();

		// Find existing booking for the same customer and service
		BookService existingBooking = bookServiceRepository.findByCustomerAndServiceProviderService(customer, serviceProviderService);

		if (existingBooking != null) {
			// If existing booking found, update its status and details
			existingBooking.setStatus(Status.REBOOK_REQUEST_SENT);
			existingBooking.setComments(Message.SERVICE_REBOOK.getMessage());
			existingBooking.setBookedAt(new Date());
		} else {
			// If no existing booking found, create a new booking
			existingBooking = new BookService();
			existingBooking.setServiceProviderService(serviceProviderService);
			existingBooking.setCustomer(customer);
			existingBooking.setStatus(Status.REBOOK_REQUEST_SENT);
			existingBooking.setComments(Message.SERVICE_REBOOK.getMessage());
			existingBooking.setBookedAt(new Date());
		}

		// Save the updated or new booking
		existingBooking = bookServiceRepository.save(existingBooking);

		// Update or create CustomerServiceStatus
//		CustomerServiceStatus customerServiceStatus = customerServiceStatusRepository.findByCustomerAndServices(customer, serviceProviderService.getService());
		CustomerServiceStatus customerServiceStatus = new CustomerServiceStatus();
		customerServiceStatus.setCustomer(customer);
		customerServiceStatus.setServices(serviceProviderService.getService());
		customerServiceStatus.setStatus(Status.REBOOK_REQUEST_SENT);
		customerServiceStatus.setUpdatedAt(new Date());
		customerServiceStatusRepository.save(customerServiceStatus);

		ServiceProviderActivity serviceProviderActivity = new ServiceProviderActivity();
		serviceProviderActivity.setStatus(Status.REQUEST_SENT);
		serviceProviderActivity.setServices(serviceProviderService.getService());
		serviceProviderActivity.setServiceProvider(serviceProviderService.getServiceProvider());
		serviceProviderActivity.setStatus(Status.REBOOK_REQUEST_SENT);
		serviceProviderActivity.setCustomer(customer);
		serviceProviderActivity.setUpdatedAt(new Date());
		serviceProviderActivityRepository.save(serviceProviderActivity);

		// Update or create BookedServiceStatus
		BookedServiceStatus bookedServiceStatus = bookedServiceStatusRepository.findByBookServiceAndServiceProvider(existingBooking, serviceProviderService.getServiceProvider());
		if (bookedServiceStatus == null) {
			bookedServiceStatus = new BookedServiceStatus();
			bookedServiceStatus.setBookService(existingBooking);
			bookedServiceStatus.setServiceProvider(serviceProviderService.getServiceProvider());
		}
		bookedServiceStatus.setComments(String.valueOf(Message.SERVICE_REBOOK.getMessage()));
		bookedServiceStatus.setStatus(Status.REBOOK_REQUEST_SENT);
		bookedServiceStatus.setUpdatedAt(new Date());
		bookedServiceStatus.setStatusChangedAt(new Date());
		bookedServiceStatusRepository.save(bookedServiceStatus);

		return existingBooking;
	}

	@Override
	public BookedServiceStatus updateBookedServiceStatus( BookedServiceStatus bookedServiceStatus ,int id ) {
		Optional<BookedServiceStatus> findBookedService = bookedServiceStatusRepository.findById(id);
		Optional<BookService> findBookedServiceInBookServiceRepo = bookServiceRepository.findById(id);
		if (findBookedService.isPresent()) {
			BookedServiceStatus existingBookedServiceStatus = findBookedService.get();
			existingBookedServiceStatus.setStatus(bookedServiceStatus.getStatus());
//			bookedServiceGet.setServiceProvider(bookedServiceStatus.getServiceProvider());
			existingBookedServiceStatus.setUpdatedAt(new Date());
			existingBookedServiceStatus.setComments(bookedServiceStatus.getComments());
			existingBookedServiceStatus.setStatusChangedAt(new Date());
			existingBookedServiceStatus.setAdditionalMessage(bookedServiceStatus.getAdditionalMessage());
			existingBookedServiceStatus.setScheduledDateTime(bookedServiceStatus.getScheduledDateTime());
			existingBookedServiceStatus.setFinishedDateTime(bookedServiceStatus.getFinishedDateTime());
			existingBookedServiceStatus.setTotalTimeTaken(bookedServiceStatus.getTotalTimeTaken());
			if(findBookedServiceInBookServiceRepo.isPresent()){
				BookService bookService = findBookedServiceInBookServiceRepo.get();
				bookService.setUpdatedAt(new Date());
				bookService.setComments(bookedServiceStatus.getComments());
				bookService.setStatus(bookedServiceStatus.getStatus());
				bookService.setFinishedDateTime(bookedServiceStatus.getFinishedDateTime());
				bookService.setScheduledDateTime(bookedServiceStatus.getScheduledDateTime());
				bookService.setAdditionalMessage(bookedServiceStatus.getAdditionalMessage());
				bookService.setTotalTimeTaken(bookedServiceStatus.getTotalTimeTaken());
				bookServiceRepository.save(bookService);
			}
			CustomerServiceStatus customerServiceStatus = new CustomerServiceStatus();
			var customer = findBookedServiceInBookServiceRepo.get().getCustomer();
			var service = findBookedServiceInBookServiceRepo.get().getServiceProviderService().getService();
			customerServiceStatus.setCustomer(customer);
			customerServiceStatus.setUpdatedAt(new Date());
			customerServiceStatus.setStatus(bookedServiceStatus.getStatus());
			customerServiceStatus.setServices(service);
			customerServiceStatus.setScheduledDateTime(bookedServiceStatus.getScheduledDateTime());
			customerServiceStatus.setFinishedDateTime(bookedServiceStatus.getFinishedDateTime());
			customerServiceStatus.setTotalTimeTaken(bookedServiceStatus.getTotalTimeTaken());
			customerServiceStatusRepository.save(customerServiceStatus);

			ServiceProviderActivity serviceProviderActivity = new ServiceProviderActivity();
			serviceProviderActivity.setServiceProvider(findBookedServiceInBookServiceRepo.get().getServiceProviderService()
																						 .getServiceProvider());
			serviceProviderActivity.setUpdatedAt(new Date());
			serviceProviderActivity.setStatus(bookedServiceStatus.getStatus());
			serviceProviderActivity.setServices(service);
			serviceProviderActivity.setScheduledDateTime(bookedServiceStatus.getScheduledDateTime());
			serviceProviderActivity.setFinishedDateTime(bookedServiceStatus.getFinishedDateTime());
			serviceProviderActivity.setComments(bookedServiceStatus.getComments());
			serviceProviderActivity.setTotalTimeTaken(bookedServiceStatus.getTotalTimeTaken());
			serviceProviderActivity.setCustomer(customer);
			serviceProviderActivityRepository.save(serviceProviderActivity);

			return bookedServiceStatusRepository.save(existingBookedServiceStatus);
		} else {
			throw new IllegalArgumentException("BookedServiceStatus with id " + id + " not found.");
		}
	}

	@Override
	public BookService cancelBooking(int id) {
		Optional<BookService> findBooking = bookServiceRepository.findById(id);
		if(findBooking.isPresent()){
			var cancelBookedService = findBooking.get();
			var authentication = SecurityContextHolder.getContext().getAuthentication();
			var username = authentication.getName();
			Customer customer = customerRepository.findByUserName(username);
			if (!cancelBookedService.getCustomer().getUserName().equals(username)) {
				throw new IllegalStateException("You are not authorized to cancel this booking.");
			}

			cancelBookedService.setStatus(Status.CANCEL_BY_USER);
			cancelBookedService.setComments(Message.SERVICE_BOOKING_CANCELLED.getMessage());
			cancelBookedService.setUpdatedAt(new Date());
			cancelBookedService.setCustomer(customer);

			BookedServiceStatus bookedServiceStatus = bookedServiceStatusRepository.findByBookService(cancelBookedService);

			if (bookedServiceStatus != null) {
				bookedServiceStatus.setStatus(Status.CANCEL_BY_USER);
				bookedServiceStatus.setCustomer(customer);
				bookedServiceStatus.setComments(Message.SERVICE_BOOKING_CANCELLED.getMessage());
				bookedServiceStatus.setUpdatedAt(new Date());
				bookedServiceStatusRepository.save(bookedServiceStatus);
			}
			//For customer service table
			CustomerServiceStatus customerServiceStatus= new CustomerServiceStatus();
			customerServiceStatus.setStatus(Status.CANCEL_BY_USER);
			customerServiceStatus.setCustomer(customer);
			customerServiceStatus.setServices(cancelBookedService.getServiceProviderService()
																 .getService());
			customerServiceStatus.setUpdatedAt(new Date());
			customerServiceStatusRepository.save(customerServiceStatus);

			ServiceProviderActivity serviceProviderActivity = new ServiceProviderActivity();
			serviceProviderActivity.setServiceProvider(findBooking.get().getServiceProviderService()
																  .getServiceProvider());
			serviceProviderActivity.setComments(Message.SERVICE_BOOKING_CANCELLED.getMessage());
			serviceProviderActivity.setServices(findBooking.get().getServiceProviderService()
														   .getService());
			serviceProviderActivity.setUpdatedAt(new Date());
			serviceProviderActivity.setStatus(Status.CANCEL_BY_USER);
			serviceProviderActivity.setCustomer(customer);
			serviceProviderActivityRepository.save(serviceProviderActivity);

			bookServiceRepository.save(cancelBookedService);
		}
		return null;
	}

	@Override
	public String viewBookedServiceStatus(String username, int serviceId) {
		return bookServiceRepository.findStatusByCustomerUserNameAndServiceProviderService_ServiceId(username , serviceId);
	}

	@Override
	public List<BookService> viewBookingByServiceProviderUsername(String username) {
		return bookServiceRepository.findByServiceProviderService_ServiceProvider_UserName(username);
	}

	@Override
	public Optional<Integer> getBookingId(String username, int serviceId) {
		BookService bookService = bookServiceRepository.findByCustomer_UserNameAndServiceProviderService_ServiceId(username, serviceId);
		if (bookService != null) {
			return Optional.of(bookService.getId()); // Assuming getId() returns an Integer
		} else {
			return Optional.empty(); // Indicate that no booking ID was found
		}
	}

	@Override
	public List<BookService> viewAllBookingDetailsByUsername(String username) {
		return bookServiceRepository.findByCustomer_UserName(username);
	}

	@Override
	public Optional<BookService> viewBookingDetailByBookingId(int bookingId) {
		return bookServiceRepository.findById(bookingId);
	}

	@Override
	public List<BookService> viewAllBooking() {
		return bookServiceRepository.findAll();
	}

}