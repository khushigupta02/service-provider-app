package com.example.myApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.Country;
import com.example.myApplication.Entity.State;

public interface CountryRepository extends JpaRepository<Country , Integer> {
	Optional<Country> findByName(String name);

}