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
import com.example.myApplication.Entity.CustomerServiceStatus;
import com.example.myApplication.Entity.Review;
import com.example.myApplication.Enum.Message;
import com.example.myApplication.Enum.Status;
import com.example.myApplication.Repository.BookServiceRepository;
import com.example.myApplication.Repository.BookedServiceStatusRepository;
import com.example.myApplication.Repository.CustomerRepository;
import com.example.myApplication.Repository.CustomerServiceStatusRepository;
import com.example.myApplication.Repository.ReviewRepository;
import com.example.myApplication.Service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository  reviewRepository;
	@Autowired
	private BookServiceRepository bookServiceRepository;
	@Autowired
	private BookedServiceStatusRepository bookedServiceStatusRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerServiceStatusRepository customerServiceStatusRepository;
	@Override
	public Review giveReview(int bookingId, Review review) {
		Optional<BookService> findBookingId = bookServiceRepository.findById(bookingId);

		if(findBookingId.isPresent()){
			var findDetails = findBookingId.get();
			var authentication = SecurityContextHolder.getContext().getAuthentication();
			var username = authentication.getName();
			Customer customer = customerRepository.findByUserName(username);
			var serviceProviderService = findDetails.getServiceProviderService();

			Review reviewObj = new Review();
			reviewObj.setCustomer(customer);
			reviewObj.setFeedback(review.getFeedback());
			reviewObj.setBookingId(bookingId);
			reviewObj.setStar(review.getStar());
			reviewObj.setServiceProviderService(serviceProviderService);
			reviewObj.setComments(Message.SERVICE_REVIEW_DONE.getMessage());
			reviewObj.setStatus(Status.REVIEW_DONE);
			reviewRepository.save(reviewObj);
			findDetails.setComments(Message.SERVICE_REVIEW_DONE.getMessage());
			findDetails.setUpdatedAt(new Date());
			findDetails.setStatus(Status.REVIEW_DONE);

			BookedServiceStatus bookedServiceStatus = bookedServiceStatusRepository.findByBookService(findDetails);
			if (bookedServiceStatus != null) {
				bookedServiceStatus.setComments(Message.SERVICE_REVIEW_DONE.getMessage());
				bookedServiceStatus.setStatus(Status.REVIEW_DONE);
				bookedServiceStatus.setCustomer(customer);
				bookedServiceStatus.setUpdatedAt(new Date());
				bookedServiceStatusRepository.save(bookedServiceStatus);
			}

			CustomerServiceStatus customerServiceStatus= new CustomerServiceStatus();
			customerServiceStatus.setStatus(Status.REVIEW_DONE);
			customerServiceStatus.setCustomer(customer);
			customerServiceStatus.setServices(findDetails.getServiceProviderService()
														 .getService());
			customerServiceStatus.setUpdatedAt(new Date());

			customerServiceStatusRepository.save(customerServiceStatus);
			bookServiceRepository.save(findDetails);

		}
		return null;
	}

	@Override
	public Review viewReview(int customerId, int serviceProviderServiceId) {
		return reviewRepository.findByCustomer_IdAndServiceProviderService_Id(customerId,serviceProviderServiceId);
	}

	@Override
	public List<Review> viewReviewOfProvider(String username) {
		return reviewRepository.findByServiceProviderService_ServiceProvider_UserName(username);
	}

	@Override
	public List<Review> viewReviewByCustomer(String username) {
		return reviewRepository.findByCustomer_UserName(username);
	}

	@Override
	public List<Review> viewReviewByServiceId(int serviceId) {
		return reviewRepository.findByAndServiceProviderService_Service_Id(serviceId);
	}

	@Override
	public List<Review> viewAllReview() {
		return reviewRepository.findAll();
	}
}