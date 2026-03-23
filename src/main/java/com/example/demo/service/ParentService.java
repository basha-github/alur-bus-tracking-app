package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BusDriver;
import com.example.demo.entity.Parent;
import com.example.demo.entity.ParentResponse;
import com.example.demo.repo.ParentRepo;

@Service
public class ParentService {

	@Autowired
	ParentRepo parentRepo;

	public void updateBusParent(String busId, String mobileNo, String password) {
		
		Parent parent = new Parent(busId,mobileNo,password);
		
		
		parentRepo.save(parent);
		
	}

	public ParentResponse getBusId(String mobileNo, String password) {
		
		
		Parent parent = parentRepo.findByMobileNumber(mobileNo);
		
		String busId = "0";
		
		if((parent != null) && parent.getBusId() != null) {
			busId = parent.getBusId();
		}
		
		return new ParentResponse(busId);
	}

	public List<Parent> getAllBusParents() {
		
		return parentRepo.findAll();
	}
}
