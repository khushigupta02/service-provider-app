package com.example.myApplication.Service.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myApplication.Entity.Role;
import com.example.myApplication.Repository.RoleRepository;
import com.example.myApplication.Service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role addRole(Role role) {
		Role requestRole = roleRepository.findByRoleName(role.getRoleName());
		if (requestRole == null) {
			return roleRepository.save(role);
		} else {
			throw new RuntimeException("Role with name '" + role.getRoleName() + "' already exists");
		}
	}

	@Override
	public List<Role> viewAllRole(Role role) {
		return roleRepository.findAll();
	}

}