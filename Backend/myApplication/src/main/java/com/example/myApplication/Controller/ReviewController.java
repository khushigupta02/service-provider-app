package com.example.myApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.Review;
import com.example.myApplication.Service.ReviewService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	@PreAuthorize("hasAuthority('Customer')")
	@PostMapping("/giveFeedback/{bookingId}")
	public Review giveFeedback(@PathVariable int bookingId , @RequestBody Review review){
		return reviewService.giveReview(bookingId,review);
	}
	@GetMapping("/viewAllReview")
	public List<Review> viewAllReview(){
		return reviewService.viewAllReview();
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping("/viewReview/{customerId}/{serviceProviderServiceId}")
	public Review viewReview(@PathVariable int customerId, @PathVariable int serviceProviderServiceId){
		return reviewService.viewReview(customerId,serviceProviderServiceId);
	}
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping("/viewReviewOfProvider/{username}")
	public List<Review> viewReviewOfProvider(@PathVariable String username){
		return reviewService.viewReviewOfProvider(username);
	}
	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewReviewByCustomer/{username}")
	public List<Review> viewReviewByCustomer(@PathVariable String username){
		return reviewService.viewReviewByCustomer(username);
	}

	@PreAuthorize("hasAuthority('Customer')")
	@GetMapping("/viewReviewByServiceId/{serviceId}")
	public List<Review> viewReviewByServiceId(@PathVariable int serviceId){
		return reviewService.viewReviewByServiceId(serviceId);
	}
}