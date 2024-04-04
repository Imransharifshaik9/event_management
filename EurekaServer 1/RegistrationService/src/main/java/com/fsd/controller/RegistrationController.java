package com.fsd.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.entity.Registration;
import com.fsd.feign.EventClient;
import com.fsd.feign.UserClient;
import com.fsd.service.RegistrationService;

import jakarta.ws.rs.Path;
@RestController
@RequestMapping("/registrations")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "http://localhost:3000", methods =  {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}, maxAge = 3600L, exposedHeaders = "Access-Control-Allow-Origin")
public class RegistrationController {

	private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    
    @Autowired
    EventClient eClient;
    
    @Autowired
    UserClient uClient;
    
    
    @PostMapping("/create/{eventId}/{userId}")
    public ResponseEntity<Registration> createRegistration(
            @PathVariable Long eventId,
            @PathVariable Long userId,
            @RequestParam int numberOfAdults,
            @RequestParam int numberOfChildren) {
        try {
            Registration registration = registrationService.createRegistration(eventId, userId, numberOfAdults, numberOfChildren);
            return new ResponseEntity<>(registration, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.getAllRegistrations();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationService.getRegistrationById(id);
       eClient.getEventById(registration.getEventId());
       uClient.getUserById(registration.getUserId());
        
        if (registration != null) {
            return new ResponseEntity<>(registration, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/count")
//    public ResponseEntity<List<Object[]>> countRegistrationsByUsernameAndEventName() {
//        List<Object[]> result = registrationService.countRegistrationsByUsernameAndEventName();
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//    
//    
//    @GetMapping("/registrations/countPerEvent")
//    public ResponseEntity<List<Object[]>> countRegistrationsPerEvent() {
//        List<Object[]> result = registrationService.countRegistrationsPerEvent();
//        return ResponseEntity.ok().body(result);
//    }
//    
    
    
    @GetMapping("/user/{userId}")
    public List<Registration> getRegistrationsByUserId(@PathVariable Long userId) {
        return registrationService.getRegistrationsByUserId(userId);
    }

    @GetMapping("/user/{userId}/count")
    public int getRegistrationCountByUserId(@PathVariable Long userId) {
        return registrationService.getRegistrationCountByUserId(userId);
    }
    
    
    
    @GetMapping("/count/{eventId}")
    public int getRegistrationCountByEventId(@PathVariable Long eventId) {
        return registrationService.countRegistrationsByEventId(eventId);
    }
    
    @PostMapping("/status/{rId}")
    public void setPaymentStatus(@PathVariable Long rId) {
    	registrationService.updatePaymentStatus(rId);
		
    	
    }
    @PostMapping("/setTId/{registrationId}/{tId}")
    public void setTicketId(@PathVariable Long registrationId,@PathVariable String tId) {
    	registrationService.setTicketId(registrationId,tId);
    }
    
    @GetMapping("/getTId/{registrationId}")
    public String getTicketId(@PathVariable Long registrationId) {
    	return registrationService.getTicketId(registrationId);
    }
    
    
    
    
    

}
