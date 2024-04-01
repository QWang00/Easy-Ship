package com.easysupplychain.controller;

import com.easysupplychain.entity.Payment;
import com.easysupplychain.entity.Payment;
import com.easysupplychain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public String findAllPayments(Model model) {
        List<Payment> payments = paymentService.findAllPayments();
        model.addAttribute("payments", payments);
        return "payments";
    }

    @GetMapping("/add-payment")
    public String showCreatePayment(Payment payment) {
        return "add-payment";
    }

    @PostMapping("/save-payment")
    public String savePayment(Payment payment, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-payment";
        paymentService.createPayment(payment);
        model.addAttribute("payments", paymentService.findAllPayments());
        return "redirect:/payments";
    }

}