package com.example.myApplication.Service.JWTService;

import com.example.myApplication.Authentication.UserInfoDetails;
import com.example.myApplication.Entity.User;
import com.example.myApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
//	public UserDetails loadUserByUsername(String username) {
//		Optional<User> userDetail = repository.findByUserName(username);
//		return userDetail.map(UserInfoDetails::new)
//						 .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
//	}

	public UserDetails loadUserByUsername(String userName) {

		Optional<User> userDetail = repository.findByUserName(userName);

		// Converting userDetail to UserDetails
		return userDetail.map(UserInfoDetails::new)
						 .orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
	}
//	public String addUser(UserInfo userInfo) {
//		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
//		repository.save(userInfo);
//		return "User Added Successfully";
//	}


}