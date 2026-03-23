package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BusDriver;

public interface BusDriverRepo   extends JpaRepository<BusDriver, String>{

	BusDriver findByMobileNumber(String mobileNo);

}
