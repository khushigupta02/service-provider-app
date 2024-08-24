package com.example.myApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.City;
import com.example.myApplication.Entity.State;

public interface CityRepository extends JpaRepository<City,Integer> {
	Optional<City> findByNameAndState(String name, State state);

}