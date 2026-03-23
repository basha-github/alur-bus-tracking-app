package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Parent;
import com.example.demo.entity.ParentResponse;
import com.example.demo.service.ParentService;

@RestController
public class ParentController {

	
	
	@Autowired
	private ParentService parentService;
	
	
	@GetMapping("/bus/parent/update")
	public String   updateBusParent(@RequestParam String busId,@RequestParam String mobileNo,
			@RequestParam String password) {
		
		
		parentService.updateBusParent(busId,mobileNo,password);
		
		return "Successfully saved Parent ---->"+mobileNo+":::::::for bus ID--->"+busId;
		
	}
	
	
	
	@GetMapping("/bus/parent/busId")
	public ParentResponse getBusIdForParent(@RequestParam String mobileNo,@RequestParam String password) {
		
		return parentService.getBusId(mobileNo,password);
		
	}
	
	
	
	@GetMapping("/bus/get/all/parents")
	public List<Parent> getAllBusParents() {
		
		return parentService.getAllBusParents();
		
	}
}
