package com.example.myApplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myApplication.Entity.SocialMedia;
import com.example.myApplication.Service.SocialMediaService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SocialMediaController {
	@Autowired
	private SocialMediaService socialMediaService;
	@PreAuthorize("hasAuthority('Service Provider')")
	@GetMapping("/viewAllSocialMedia")
	public List<SocialMedia> viewAllSocialMedia(){
		return socialMediaService.viewAllSocialMedia();
	}

}