package com.example.myApplication.Service;

import java.util.List;

import com.example.myApplication.Entity.Review;

public interface ReviewService {
    Review giveReview(int bookingId , Review review );
	Review viewReview(int customerId, int serviceProviderServiceId);
	List<Review> viewReviewOfProvider(String username);
	List<Review> viewReviewByCustomer(String username);
	List<Review> viewReviewByServiceId(int serviceId);
	List<Review> viewAllReview();

}