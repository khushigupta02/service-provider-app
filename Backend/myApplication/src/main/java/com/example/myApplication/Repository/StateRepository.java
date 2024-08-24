package com.example.myApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.Country;
import com.example.myApplication.Entity.State;

public interface StateRepository extends JpaRepository<State , Integer> {
	Optional<State> findByNameAndCountry(String name, Country country);

}