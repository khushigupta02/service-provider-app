package com.example.myApplication.Service.DashboardService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.User;
import com.example.myApplication.Repository.UserRepository;

@Service
public class CustomerDashboardService  {

//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) {
//		User user = userRepository.findByUserName(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority(user.getUserType().getRoleName()));
//
//		return new org.springframework.security.core.userdetails.User(
//				user.getUserName(),
//				user.getPassword(),
//				authorities
//		);
//	}
}