package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BusDriver;
import com.example.demo.entity.BusDriverResponse;
import com.example.demo.repo.BusDriverRepo;

@Service
public class BusDriverService {
	
	@Autowired
	BusDriverRepo busDriverRepo;

	public void updateBusDriver(String busId, String mobileNo, String password) {
		
		BusDriver busDriver = new BusDriver(busId,mobileNo,password);
		
		
		busDriverRepo.save(busDriver);
		
	}

	public BusDriverResponse getBusId(String mobileNo, String password) {
		
		
		BusDriver busDriver = busDriverRepo.findByMobileNumber(mobileNo);
		
		String busId = "0";
		
		if((busDriver != null) &&busDriver.getBusId() != null) {
			return  new BusDriverResponse(busDriver.getBusId());
		}
		
		return new BusDriverResponse(busId);
	}

	public List<BusDriver> getAllBusDrivers() {
		
		return busDriverRepo.findAll();
	}

}
