package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.SocialMedia;

public interface SocialMediaRepository extends JpaRepository<SocialMedia , Integer> {
}