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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.myApplication.Enum.Status;

@Entity
@Table(name = "booked_service_status")
public class BookedServiceStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booked_service_id", referencedColumnName = "id")
	private BookService bookService;
	@Enumerated(EnumType.STRING)
	private Status status;

	private String comments;
	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date statusChangedAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_updated_by_service_provider")
	private ServiceProvider serviceProvider;
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "status_updated_by_customer")
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getStatusChangedAt() {
		return statusChangedAt;
	}

	public void setStatusChangedAt(Date statusChangedAt) {
		this.statusChangedAt = statusChangedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
}