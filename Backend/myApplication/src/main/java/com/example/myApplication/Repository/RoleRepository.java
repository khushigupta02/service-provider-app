package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myApplication.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role , Integer> {
	Role findByRoleName(String roleName);

}