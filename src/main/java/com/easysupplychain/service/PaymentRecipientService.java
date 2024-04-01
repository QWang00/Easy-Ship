package com.easysupplychain.service;
import com.easysupplychain.entity.PaymentRecipient;
import com.easysupplychain.repository.PaymentRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentRecipientService {
    @Autowired
    private PaymentRecipientRepository paymentRecipientRepository;

    public List<PaymentRecipient> findAllPaymentRecipients() {
        return paymentRecipientRepository.findAll();
    }

    public PaymentRecipient findPaymentRecipientById(Long id) {
        PaymentRecipient paymentRecipient = paymentRecipientRepository.findById(id).orElseThrow(() ->new RuntimeException("PaymentRecipient Not found"));
        return paymentRecipient;
    }

    public void createPaymentRecipient(PaymentRecipient paymentRecipient){
        paymentRecipientRepository.save(paymentRecipient);
    }

    public void updatePaymentRecipient(PaymentRecipient paymentRecipient){
        paymentRecipientRepository.save(paymentRecipient);
    }

    public void deletePaymentRecipient(Long id){
        PaymentRecipient paymentRecipient = paymentRecipientRepository.findById(id).orElseThrow(()-> new RuntimeException("PaymentRecipient Not Found"));
        paymentRecipientRepository.deleteById(paymentRecipient.getId());
    }
}
