package com.example.myApplication.Service;

import java.util.List;

import com.example.myApplication.Entity.Role;

public interface RoleService {
	Role addRole(Role role);
	List<Role> viewAllRole(Role role);
}