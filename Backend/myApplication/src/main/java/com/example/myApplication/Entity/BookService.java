package com.example.myApplication.Entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.myApplication.Enum.Status;

@Entity
@Table(name = "book_service_customer")
public class BookService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreationTimestamp
	@Column(updatable = false, name = "booked_at")
	private Date bookedAt;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(name = "scheduled_at", nullable = true)
	private Date scheduledDateTime;
	@Column( name = "finished_at", nullable = true)
	private Date finishedDateTime;
	@Column(nullable = true)
	private String additionalMessage;
	@Column(name = "total_time_taken", nullable = true)
	private String totalTimeTaken;

	public String getTotalTimeTaken() {
		return totalTimeTaken;
	}

	public void setTotalTimeTaken(String totalTimeTaken) {
		this.totalTimeTaken = totalTimeTaken;
	}

	public Date getScheduledDateTime() {
		return scheduledDateTime;
	}

	public void setScheduledDateTime(Date scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
	}

	public Date getFinishedDateTime() {
		return finishedDateTime;
	}

	public void setFinishedDateTime(Date finishedDateTime) {
		this.finishedDateTime = finishedDateTime;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne()
	@JoinColumn(name = "customer_id" )
	private Customer customer;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "service_with_provider_id", referencedColumnName = "id")
	private ServiceProviderService serviceProviderService;

	private String comments;
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "service_status_updated_by_provider", referencedColumnName = "id")
//	private ServiceProvider serviceProvider;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBookedAt() {
		return bookedAt;
	}

	public void setBookedAt(Date bookedAt) {
		this.bookedAt = bookedAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ServiceProviderService getServiceProviderService() {
		return serviceProviderService;
	}

	public void setServiceProviderService(ServiceProviderService serviceProviderService) {
		this.serviceProviderService = serviceProviderService;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

//	public ServiceProvider getServiceProvider() {
//		return serviceProvider;
//	}
//
//	public void setServiceProvider(ServiceProvider serviceProvider) {
//		this.serviceProvider = serviceProvider;
//	}
}