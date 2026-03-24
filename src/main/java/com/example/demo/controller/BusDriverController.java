package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.BusDriver;
import com.example.demo.entity.BusDriverResponse;
import com.example.demo.service.BusDriverService;

@RestController
@CrossOrigin("*")
public class BusDriverController {
	
	
	@Autowired
	private BusDriverService busDriverService;
	
	
	
		@GetMapping("/bus/driver/update")
	public String   updateBusDriver(@RequestParam String busId,@RequestParam String mobileNo,
			@RequestParam String password) {
		
		
		busDriverService.updateBusDriver(busId,mobileNo,password);
		
		return "Successfully saved Driver ---->"+mobileNo+":::::::for bus ID--->"+busId;
		
	}
	
	@GetMapping("/bus/driver/busId")
	public BusDriverResponse getBusIdForDriver(@RequestParam String mobileNo,@RequestParam String password) {
		
		System.out.println("mobileNo--------->"+mobileNo);
		System.out.println("password--------->"+password);
		
		return busDriverService.getBusId(mobileNo,password);
		
	}
	
	@GetMapping("/bus/get/all/drivers")
	public List<BusDriver> getAllBusDrivers() {
		
		return busDriverService.getAllBusDrivers();
		
	}
	
	

}
