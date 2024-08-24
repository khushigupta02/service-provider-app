package com.example.myApplication.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import jakarta.persistence.OneToMany;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.myApplication.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "serviceProvider")
public class ServiceProvider {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;

	private String userName;
	private String email;
	private String serviceName;
	private String serviceDescription;
	@Column(nullable = true)
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Column(name = "date_of_birth", nullable = true)
	private LocalDate dob;
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@ManyToOne
	private Address address;
//	@OneToMany( cascade = CascadeType.PERSIST)
//	@JoinColumn(name = "service_id" , referencedColumnName = "id")
//	private List<Services> providedServices = new ArrayList<>();

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "service_id")
//	private Services service;
//
//	public Services getService() {
//		return service;
//	}
//
//	public void setService(Services service) {
//		this.service = service;
//	}

	@OneToMany(mappedBy = "serviceProvider")
	@JsonIgnore
	private List<ServiceProviderService> serviceProviderServices = new ArrayList<>();

	public List<ServiceProviderService> getServiceProviderServices() {
		return serviceProviderServices;
	}

	public void setServiceProviderServices(List<ServiceProviderService> serviceProviderServices) {
		this.serviceProviderServices = serviceProviderServices;
	}

	@Column(nullable = true)
	private String bio;
	@OneToOne( cascade = CascadeType.ALL)
	private SocialMedia socialMedia;
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	//	@ManyToOne
//	@JoinColumn(name = "service_type_id", referencedColumnName = "id")
//	private ServiceType serviceType;

	private double hourlyRate;
	@CreationTimestamp
	@Column(updatable = false, name = "created_at")
	private Date createdAt;
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Date updatedAt;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public SocialMedia getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(SocialMedia socialMedia) {
		this.socialMedia = socialMedia;
	}
}