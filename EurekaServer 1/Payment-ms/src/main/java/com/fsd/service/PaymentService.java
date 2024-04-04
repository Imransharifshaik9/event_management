package com.fsd.service;
import java.time.LocalDateTime;
import com.fsd.feign.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsd.entity.Payment;
import com.fsd.entity.Registration;
import com.fsd.feign.RegistrationClient;
import com.fsd.repository.PaymentRepository;

@Service
public class PaymentService {

//    @Autowired
//    private RegistrationRepository registrationRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    RegistrationClient rClient;
    
    @Autowired
    EventClient eClient;

    public String makePayment(Long registrationId, String paymentMethod) {

        double paymentAmount = calculatePaymentAmount(registrationId);
        Payment payment = new Payment();
        payment.setAmount(paymentAmount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod);
        payment.setRegistrationId(registrationId);
        rClient.setPaymentStatus(registrationId);

        paymentRepository.save(payment);
        
        return paymentAmount+" Payment successful. Thank you for your payment! ";
    }
    
    
    
    

    
//    private String generateQRCode(Payment payment) {
//        String paymentInfo = "Payment ID: " + payment.getId() + ", Amount: " + payment.getAmount() +
//                ", Payment Date: " + payment.getPaymentDate() + ", Payment Method: " + payment.getPaymentMethod();
//        byte[] qrCodeBytes = generateQRCodeImage(paymentInfo);
// 
//        return Base64.getEncoder().encodeToString(qrCodeBytes);
//    }
//
//    private byte[] generateQRCodeImage(String text) {
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            int size = 300;
//            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//            Graphics2D graphics = image.createGraphics();
//            graphics.setColor(Color.WHITE);
//            graphics.fillRect(0, 0, size, size);
//            graphics.setColor(Color.BLACK);
//            ImageIO.write(image, "PNG", stream);
//            return stream.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new byte[0];
//        }
//    }
//
  public double calculatePaymentAmount(Long registrationId) {
    	Long eventId=rClient.getRegistrationById(registrationId).getEventId();
    	   	System.out.println(registrationId+"---"+eventId);
    	   	
    	   	
    	   	double entryFee=eClient.getEventById(eventId).getEntryFee();
    	   	
    		System.out.println();
    	
    	double pricePerAdult=eClient.getEventById(eventId).getFoodPriceAdult();
    	double pricePerChild=eClient.getEventById(eventId).getFoodPriceChild();
       double noOfAdults=rClient.getRegistrationById(registrationId).getNumberOfAdults();
       double noOfChildren=rClient.getRegistrationById(registrationId).getNumberOfChildren();
       
        return (entryFee * (noOfAdults+noOfChildren)) + (noOfAdults*pricePerAdult+noOfChildren*pricePerChild);
    }
  
  
  
//    private double calculateFoodCost(List<FoodOption> foodOptions, int numberOfAdults, int numberOfChildren) {
//        double totalFoodCost = 0.0;
//        for (FoodOption foodOption : foodOptions) {
//            totalFoodCost += (foodOption.getPrice() * numberOfAdults) + (foodOption.getPrice() * 0.5 * numberOfChildren);
//        }
//
//        return totalFoodCost;
//    }
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).get();
    }

    public List<Payment> getPaymentsByRegistrationId(Long registrationId) {
        return paymentRepository.findByRegistrationId(registrationId);
    }
    
}

