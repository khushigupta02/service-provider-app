package com.example.myApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.myApplication.Entity.Role;
import com.example.myApplication.Service.RoleService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RoleController {
	@Autowired
	private RoleService roleService;
  	@PostMapping("/addRole")
	public Role addRole(@RequestBody Role role){
		return roleService.addRole(role);
	}
	@GetMapping("/viewAllRole")
	public List<Role> viewAllRole(Role role){
		return roleService.viewAllRole(role);
	}
}