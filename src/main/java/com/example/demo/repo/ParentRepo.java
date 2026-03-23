package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Parent;

public interface ParentRepo  extends JpaRepository<Parent, String> {

	Parent findByMobileNumber(String mobileNo);

}
