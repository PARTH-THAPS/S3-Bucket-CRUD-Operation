package com.S3.Bucket.Demo;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.S3.Bucket.Demo.controller.S3Controller;

import DTO.Merchant;
import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

	
	  @Autowired
	    private S3Controller s3Controller;

	    @PostConstruct
	    public void initData() {
	    	s3Controller.download("merchant.json");
	    System.out.println("DATA Downloaded");
	        s3Controller.downloadData();
	    }
	
}
