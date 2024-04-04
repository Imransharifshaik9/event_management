package com.fsd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fsd.controller.PaymentController;
import com.fsd.entity.Payment;
import com.fsd.service.PaymentService;

public class PaymentControllerTest {

    private PaymentService paymentService;
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        paymentService = mock(PaymentService.class);
        paymentController = new PaymentController(paymentService);
    }

    @Test
    public void testMakePayment() {
        Long registrationId = 1L;
        String paymentMethod = "Credit Card";
        String paymentResult = "Success";

        when(paymentService.makePayment(registrationId, paymentMethod)).thenReturn(paymentResult);

        ResponseEntity<String> responseEntity = paymentController.makePayment(registrationId, paymentMethod);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentResult, responseEntity.getBody());
    }

    @Test
    public void testGetPaymentById() {
        Long paymentId = 1L;
        Payment payment = new Payment(); 

        when(paymentService.getPaymentById(paymentId)).thenReturn(payment);

        ResponseEntity<Payment> responseEntity = paymentController.getPaymentById(paymentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(payment, responseEntity.getBody());
    }

    @Test
    public void testGetPaymentsByRegistrationId() {
        Long registrationId = 1L;
        List<Payment> payments = new ArrayList<>(); 

        when(paymentService.getPaymentsByRegistrationId(registrationId)).thenReturn(payments);

        ResponseEntity<List<Payment>> responseEntity = paymentController.getPaymentsByRegistrationId(registrationId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(payments, responseEntity.getBody());
    }
}
