package com.example.myApplication.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.myApplication.Entity.User;
public interface UserService {
	User addUser( User user);
	User userLogin(User user);
	List<User> viewAll(User user);
	User editUser(User user , int id );
	User deleteUser(int id);
	Optional<User> findByUserName(String username);

}