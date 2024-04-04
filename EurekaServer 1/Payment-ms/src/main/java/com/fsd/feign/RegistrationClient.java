package com.fsd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fsd.entity.Registration;


@FeignClient(name = "RegistrationService", url = "http://localhost:7003")
public interface RegistrationClient {
	
	 @GetMapping("/registrations/{id}")
	 Registration getRegistrationById(@PathVariable Long id);
	 
	 @PostMapping("/registrations/status/{rId}")
	 public String setPaymentStatus(@PathVariable Long rId) ;

}
