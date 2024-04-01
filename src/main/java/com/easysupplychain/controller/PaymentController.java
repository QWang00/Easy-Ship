package com.easysupplychain.controller;

import com.easysupplychain.entity.Payment;
import com.easysupplychain.entity.Payment;
import com.easysupplychain.entity.Payment;
import com.easysupplychain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("remove-payment/{id}")
    public String removePayment(@PathVariable Long id, Model model){
        paymentService.deletePayment(id);
        model.addAttribute("payments", paymentService.findAllPayments());
        return "payments";
    }

    @PostMapping("update-payment/{id}")
    public String saveUpdatePayment(@PathVariable Long id, Payment payment, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors())
            return "update-payment";
        paymentService.updatePayment(payment);
        model.addAttribute("payments", paymentService.findAllPayments());
        return "redirect:/payments";
    }
    @GetMapping("update-payment/{id}")
    public String updatePayment(@PathVariable Long id, Model model){
        model.addAttribute("payment", paymentService.findPaymentById(id));
        return "update-payment";
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