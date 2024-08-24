package com.example.myApplication.Service.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.myApplication.Entity.Address;
import com.example.myApplication.Entity.City;
import com.example.myApplication.Entity.Country;
import com.example.myApplication.Entity.Customer;
import com.example.myApplication.Entity.Role;
import com.example.myApplication.Entity.ServiceProvider;
import com.example.myApplication.Entity.ServiceProviderService;
import com.example.myApplication.Entity.ServiceType;
import com.example.myApplication.Entity.Services;
import com.example.myApplication.Entity.SocialMedia;
import com.example.myApplication.Entity.State;
import com.example.myApplication.Entity.User;
import com.example.myApplication.Repository.AddressRepository;
import com.example.myApplication.Repository.CityRepository;
import com.example.myApplication.Repository.CountryRepository;
import com.example.myApplication.Repository.CustomerRepository;
import com.example.myApplication.Repository.RoleRepository;
import com.example.myApplication.Repository.ServiceProviderRepository;
import com.example.myApplication.Repository.ServiceProviderServiceRepository;
import com.example.myApplication.Repository.ServiceRepository;
import com.example.myApplication.Repository.ServiceTypeRepository;
import com.example.myApplication.Repository.SocialMediaRepository;
import com.example.myApplication.Repository.StateRepository;
import com.example.myApplication.Repository.UserRepository;
import com.example.myApplication.Service.ServiceProviderServiceService;
import com.example.myApplication.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SocialMediaRepository socialMediaRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ServiceProviderRepository serviceProviderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private ServiceProviderServiceRepository serviceProviderServiceRepository;

	@Override
	public User addUser(User user) {
		var userTypeReq = user.getUserType().getRoleName();
		var useNameReq = user.getUserName();
		var emailReq = user.getEmail();
		var dbUserType = roleRepository.findByRoleName(userTypeReq);
		var userName = userRepository.findByUserName(useNameReq);
		var userEmail = userRepository.findByEmail(emailReq);

		if (userName.isPresent()) {
			throw new RuntimeException("This Username Already Exists, Try with Another Username");
		}
		if (userEmail != null) {
			throw new RuntimeException("This Email Already Exists, Try with Another Email");
		}

		if (dbUserType == null) {
			var newRole = new Role();
			newRole.setRoleName(userTypeReq);
			dbUserType = roleRepository.save(newRole);
		}
		user.setUserType(dbUserType);

		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);

		if ("Service Provider".equals(userTypeReq)) {
			var serviceProvider = new ServiceProvider();
			serviceProvider.setFirstName(user.getFirstName());
			serviceProvider.setImage(user.getImage());
			serviceProvider.setLastName(user.getLastName());
			serviceProvider.setEmail(user.getEmail());
			serviceProvider.setUserName(user.getUserName());
			serviceProvider.setServiceName(user.getServiceName());
			serviceProvider.setServiceDescription(user.getServiceDescription());
			serviceProvider.setHourlyRate(user.getHourlyRate());
			serviceProvider.setPhoneNumber(user.getPhoneNumber());

			// Check if the service type exists
			ServiceType dbServiceType = serviceTypeRepository.findByName(user.getServiceType().getName());
			if (dbServiceType == null) {
				var newServiceType = new ServiceType();
				newServiceType.setName(user.getServiceType().getName());
				dbServiceType = serviceTypeRepository.save(newServiceType);
			}
			user.setServiceType(dbServiceType);
			Services services = new Services();
			services.setDescription(user.getServiceDescription());
			services.setServiceName(user.getServiceName());
			services.setHourlyRate(user.getHourlyRate());
			services.setServiceType(user.getServiceType());
			services.setServiceProvider(serviceProvider);

			serviceProviderRepository.save(serviceProvider);
			serviceRepository.save(services);
			ServiceProviderService serviceProviderServiceService = new ServiceProviderService();
			serviceProviderServiceService.setService(services);
			serviceProviderServiceService.setServiceProvider(serviceProvider);
			serviceProviderServiceRepository.save(serviceProviderServiceService);

		} else if ("Customer".equals(userTypeReq)) {
			var customer = new Customer();
			customer.setFirstName(user.getFirstName());
			customer.setLastName(user.getLastName());
			customer.setEmail(user.getEmail());
			customer.setUserName(user.getUserName());
			customer.setImage(user.getImage());
			customerRepository.save(customer);
		}

		// Save the User entity
		return userRepository.save(user);
	}


	@Override
	public User userLogin(User user) {
		var userNameReq = user.getUserName();
		var plainPassword = user.getPassword(); // Provided plain password
		var userType = user.getUserType().getRoleName();

		// Encode the provided plain password
//	    String encodedPassword = passwordEncoder.encode(plainPassword);
		String encodedPassword = passwordEncoder.encode(plainPassword);

		// Find the user by username and user type
		var findDBUser = userRepository.findByUserNameAndUserType_RoleName(userNameReq, userType);

		if ((findDBUser != null) && passwordEncoder.matches(plainPassword, findDBUser.getPassword())) {
			System.out.println("Login Successfully");
			return findDBUser;
		} else {
			throw new RuntimeException("User does not exist or invalid credentials");
		}
	}

	@Override
	public List<User> viewAll(User user) {
		return userRepository.findAll();
	}

	@Override
	public User editUser(User user, int id) {
		Optional<User> dbUser = userRepository.findById(id);
		if (dbUser.isPresent()) {
			User existingUser = dbUser.get();
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setGender(user.getGender());
			existingUser.setDob(user.getDob());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setBio(user.getBio());

			// Update or create social media
			SocialMedia existingSocialMedia = existingUser.getSocialMedia();
			SocialMedia userSocialMedia = user.getSocialMedia();

			if (existingSocialMedia != null && userSocialMedia != null) {
				// Update existing social media details
				existingSocialMedia.setFacebook(userSocialMedia.getFacebook());
				existingSocialMedia.setInstagram(userSocialMedia.getInstagram());
				existingSocialMedia.setLinkedin(userSocialMedia.getLinkedin());
				existingSocialMedia.setTwitter(userSocialMedia.getTwitter());
				socialMediaRepository.save(existingSocialMedia);
			} else if (userSocialMedia != null) {
				// Create new social media details if none exists
				SocialMedia newSocialMedia = new SocialMedia();
				newSocialMedia.setFacebook(userSocialMedia.getFacebook());
				newSocialMedia.setInstagram(userSocialMedia.getInstagram());
				newSocialMedia.setLinkedin(userSocialMedia.getLinkedin());
				newSocialMedia.setTwitter(userSocialMedia.getTwitter());
				socialMediaRepository.save(newSocialMedia);
				existingUser.setSocialMedia(newSocialMedia);
			}

			if ("Service Provider".equals(existingUser.getUserType().getRoleName())) {
				existingUser.setServiceName(user.getServiceName());
				existingUser.setServiceDescription(user.getServiceDescription());
				existingUser.setHourlyRate(user.getHourlyRate());

				// Update service type
				ServiceType existingServiceType = existingUser.getServiceType();
				ServiceType updatedServiceType = serviceTypeRepository.findById(user.getServiceType().getId())
																	  .orElseThrow(() -> new IllegalArgumentException("Invalid ServiceType ID"));
				existingServiceType.setName(user.getServiceType().getName());
				existingUser.setServiceType(existingServiceType);

				// Save updated service provider
				ServiceProvider serviceProvider = serviceProviderRepository.findByUserName(existingUser.getUserName());
				if (serviceProvider == null) {
					serviceProvider = new ServiceProvider();
					serviceProvider.setUserName(existingUser.getUserName());
				}
				serviceProvider.setFirstName(existingUser.getFirstName());
				serviceProvider.setLastName(existingUser.getLastName());
				serviceProvider.setEmail(existingUser.getEmail());
				serviceProvider.setServiceName(existingUser.getServiceName());
				serviceProvider.setServiceDescription(existingUser.getServiceDescription());
				serviceProvider.setHourlyRate(existingUser.getHourlyRate());
				serviceProvider.setPhoneNumber(existingUser.getPhoneNumber());
				serviceProvider.setDob(existingUser.getDob());
				serviceProvider.setBio(existingUser.getBio());
				serviceProvider.setGender(existingUser.getGender());
				serviceProvider.setSocialMedia(existingUser.getSocialMedia());

				// Update or create address
				Address existingAddress = serviceProvider.getAddress();
				if (existingAddress == null) {
					existingAddress = new Address();
				}
				existingAddress.setFullAddress(user.getAddress().getFullAddress());
				existingAddress.setZipCode(user.getAddress().getZipCode());
				// Fetch or create and fetch country, state, and city
				Country country = countryRepository.findByName(user.getAddress().getCountry().getName())
												   .orElseGet(() -> {
													   Country newCountry = new Country();
													   newCountry.setName(user.getAddress().getCountry().getName());
													   return countryRepository.save(newCountry);
												   });
				State state = stateRepository.findByNameAndCountry(user.getAddress().getState().getName(), country)
											 .orElseGet(() -> {
												 State newState = new State();
												 newState.setName(user.getAddress().getState().getName());
												 newState.setCountry(country);
												 return stateRepository.save(newState);
											 });
				City city = cityRepository.findByNameAndState(user.getAddress().getCity().getName(), state)
										  .orElseGet(() -> {
											  City newCity = new City();
											  newCity.setName(user.getAddress().getCity().getName());
											  newCity.setState(state);
											  return cityRepository.save(newCity);
										  });

				existingAddress.setCountry(country);
				existingAddress.setState(state);
				existingAddress.setCity(city);
				Address savedAddress = addressRepository.save(existingAddress);
				serviceProvider.setAddress(savedAddress);

				serviceProviderRepository.save(serviceProvider);
				// Set the address ID in the user object
				existingUser.setAddress(savedAddress);
			} else if ("Customer".equals(existingUser.getUserType().getRoleName())) {
				// Save updated customer
				Customer customer = customerRepository.findByUserName(existingUser.getUserName());
				if (customer == null) {
					customer = new Customer();
					customer.setUserName(existingUser.getUserName());
				}
				customer.setFirstName(existingUser.getFirstName());
				customer.setLastName(existingUser.getLastName());
				customer.setEmail(existingUser.getEmail());
				customer.setDob(existingUser.getDob());
				customer.setBio(existingUser.getBio());
				customer.setGender(existingUser.getGender());
				customer.setPhoneNumber(existingUser.getPhoneNumber());
				customer.setSocialMedia(existingUser.getSocialMedia());

				// Update or create address
				Address existingAddress = customer.getAddress();
				if (existingAddress == null) {
					existingAddress = new Address();
				}
				existingAddress.setFullAddress(user.getAddress().getFullAddress());
				existingAddress.setZipCode(user.getAddress().getZipCode());
				// Fetch or create and fetch country, state, and city
				Country country = countryRepository.findByName(user.getAddress().getCountry().getName())
												   .orElseGet(() -> {
													   Country newCountry = new Country();
													   newCountry.setName(user.getAddress().getCountry().getName());
													   return countryRepository.save(newCountry);
												   });
				State state = stateRepository.findByNameAndCountry(user.getAddress().getState().getName(), country)
											 .orElseGet(() -> {
												 State newState = new State();
												 newState.setName(user.getAddress().getState().getName());
												 newState.setCountry(country);
												 return stateRepository.save(newState);
											 });
				City city = cityRepository.findByNameAndState(user.getAddress().getCity().getName(), state)
										  .orElseGet(() -> {
											  City newCity = new City();
											  newCity.setName(user.getAddress().getCity().getName());
											  newCity.setState(state);
											  return cityRepository.save(newCity);
										  });

				existingAddress.setCountry(country);
				existingAddress.setState(state);
				existingAddress.setCity(city);
				Address savedAddress = addressRepository.save(existingAddress);
				customer.setAddress(savedAddress);

				customerRepository.save(customer);
				// Set the address ID in the user object
				existingUser.setAddress(savedAddress);
			}

			// Save the user with updated address ID
			return userRepository.save(existingUser);
		}
		return null;
	}


	@Override
	public User deleteUser(int id) {
		var dbUser = userRepository.findById(id);
		if(dbUser.isPresent()){
			var existingUser = dbUser.get();
			userRepository.delete(existingUser);
		}
		return null;
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}