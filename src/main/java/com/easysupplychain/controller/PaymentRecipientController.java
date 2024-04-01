package com.easysupplychain.controller;

import com.easysupplychain.entity.PaymentRecipient;
import com.easysupplychain.service.PaymentRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PaymentRecipientController {

    @Autowired
    private PaymentRecipientService paymentRecipientService;

    @GetMapping("/paymentRecipients")
    public String findAllPaymentRecipients(Model model) {
        List<PaymentRecipient> paymentRecipients = paymentRecipientService.findAllPaymentRecipients();
        model.addAttribute("paymentRecipients", paymentRecipients);
        return "paymentRecipients";
    }

    @GetMapping("/add-paymentRecipient")
    public String showCreatePaymentRecipient(PaymentRecipient paymentRecipient) {
        return "add-paymentRecipient";
    }

    @PostMapping("/save-paymentRecipient")
    public String savePaymentRecipient(PaymentRecipient paymentRecipient, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "add-paymentRecipient";
        paymentRecipientService.createPaymentRecipient(paymentRecipient);
        model.addAttribute("paymentRecipients", paymentRecipientService.findAllPaymentRecipients());
        return "redirect:/paymentRecipients";
    }

}