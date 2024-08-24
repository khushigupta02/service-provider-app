package com.example.myApplication.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myApplication.Entity.SocialMedia;
import com.example.myApplication.Repository.SocialMediaRepository;
import com.example.myApplication.Service.SocialMediaService;

@Service
public class SocialMediaServiceImpl implements SocialMediaService {
	@Autowired
	private SocialMediaRepository  socialMediaRepository;
	@Override
	public List<SocialMedia> viewAllSocialMedia() {
		return socialMediaRepository.findAll();
	}
}