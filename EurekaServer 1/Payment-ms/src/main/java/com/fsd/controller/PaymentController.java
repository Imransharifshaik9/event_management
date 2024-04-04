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

import com.fsd.entity.Payment;
import com.fsd.exception.PaymentNotFoundException;
import com.fsd.service.PaymentService;

@RestController
@RequestMapping("/payments")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "http://localhost:3000", methods =  {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.HEAD}, maxAge = 3600L, exposedHeaders = "Access-Control-Allow-Origin")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Endpoint for making a payment using  registration
    @PostMapping("/makePayment/{registrationId}")
    public ResponseEntity<String> makePayment(@PathVariable Long registrationId, @RequestParam String paymentMethod) {
        String paymentResult = paymentService.makePayment(registrationId, paymentMethod);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResult);
    }
    
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            throw new PaymentNotFoundException("Payment not found for ID: " + paymentId);
        }
    }

    
    //fetching details

    @GetMapping("/registration/{registrationId}")
    public ResponseEntity<List<Payment>> getPaymentsByRegistrationId(@PathVariable Long registrationId) {
        List<Payment> payments = paymentService.getPaymentsByRegistrationId(registrationId);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/amount/{registrationId}")
    public double getPaymentAmount(@PathVariable Long registrationId) {
    	double amount=paymentService.calculatePaymentAmount(registrationId);
		return amount;
    	
    }
    
    
    

   
    
    
    
}
