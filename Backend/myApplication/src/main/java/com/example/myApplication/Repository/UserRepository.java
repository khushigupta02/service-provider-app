package com.example.myApplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
//	User findByUserName(String username);
	Optional<User> findByUserName(String userName);

	User findByEmail(String email);

	User findByUserNameAndPasswordAndUserType_RoleName(String username, String password, String userType);

	User findByUserNameAndPassword(String username, String password);
	User findByUserNameAndEmail(String username, String email);
	User findByUserNameAndUserType_RoleName(String username , String email);
	List<User> findByUserType_RoleName (String userType);
}