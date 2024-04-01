package com.easysupplychain.service;

import com.easysupplychain.entity.Payment;
import com.easysupplychain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() ->new RuntimeException("Payment Not found"));
        return payment;
    }

    public void createPayment(Payment payment){
        paymentRepository.save(payment);
    }

    public void updatePayment(Payment payment){
        paymentRepository.save(payment);
    }

    public void deletePayment(Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(()-> new RuntimeException("Payment Not Found"));
        paymentRepository.deleteById(payment.getId());
    }
}
