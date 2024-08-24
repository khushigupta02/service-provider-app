package com.example.myApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myApplication.Entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}