package com.fsd.repository;

import com.fsd.entity.Payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByRegistrationId(Long registrationId);

	//List<Payment> findByRegistrationId(Long registrationId);
    // You can add custom query methods here if needed
}
